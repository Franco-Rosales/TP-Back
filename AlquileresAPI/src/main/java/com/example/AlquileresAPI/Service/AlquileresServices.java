package com.example.AlquileresAPI.Service;

import com.example.AlquileresAPI.Entities.Alquileres;
import com.example.AlquileresAPI.Entities.Tarifa;
import com.example.AlquileresAPI.Respository.AlquileresRepository;
import com.example.AlquileresAPI.Respository.TarifaRepository;
import com.example.EstacionesAPI.Entities.Estaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlquileresServices {
    @Autowired
    private AlquileresRepository alquileresRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    public List<Alquileres> getAlquileresFinalizados(double monto){
        List<Alquileres> alquileres = alquileresRepository.findAll();

        if (monto > 0) {
            alquileres = alquileres.stream().filter(alquiler -> alquiler.getMonto() > monto).collect(Collectors.toList());
        }

        alquileres.stream().filter(alquiler -> alquiler.getFechaHoraRetiro() != null).collect(Collectors.toList());

        return alquileres;
    }

    public Alquileres addAlquiler(String idCliente, Long idEstacion){
            RestTemplate template = new RestTemplate();
            ResponseEntity<Estaciones> res = template.getForEntity("http://localhost:8083/api/estaciones/"+idEstacion, Estaciones.class);
            Estaciones estacion =  res.getBody();

            Alquileres alquiler = new Alquileres();
            alquiler.setFechaHoraRetiro(LocalDateTime.now());
            alquiler.setEstacionRetiro(estacion);
            alquiler.setEstado(1);
            alquiler.setIdCliente(idCliente);
            alquileresRepository.save(alquiler);
            return alquiler;

    }

    public Alquileres finalizarAlquiler(Long idEstacionDevolucion,String idCliente){
        Alquileres alquiler = alquileresRepository.findByEstadoAndIdCliente(1,idCliente);
        if(alquiler != null){
            RestTemplate template = new RestTemplate();
            ResponseEntity<Estaciones> res = template.getForEntity("http://localhost:8083/api/estaciones/" + idEstacionDevolucion, Estaciones.class);
            Estaciones estacion =  res.getBody();
            alquiler.setEstacionDevolucion(estacion);
            alquiler.setEstado(2);
            alquiler.setFechaHoraDevolucion(java.time.LocalDateTime.now());
            alquiler.setTarifa(determinarTarifa(alquiler));
            alquiler.setMonto(calcularMonto(alquiler));
            alquileresRepository.save(alquiler);
            return alquiler;
        }
        else{
            throw new NoSuchElementException("No se encontró un alquiler activo para el cliente.");

        }
    }

    public Tarifa determinarTarifa(Alquileres alquiler) {
        LocalDateTime fecha = alquiler.getFechaHoraDevolucion();

        // Primero checkeamos si alguna tarifa definida por día, mes y año coincide con la fecha de devolución
        List<Tarifa> tarifas = tarifaRepository.findByDefinicion("C");
        for (Tarifa t : tarifas) {
            if (t.getDiaMes() == fecha.getDayOfMonth() &&
                    t.getMes() == fecha.getMonthValue() &&
                    t.getAnio() == fecha.getYear()) {
                return t;
            }
        }

        // Si no coincide ninguna, entonces buscamos la tarifa correspondiente definida por el día de la semana
        Optional<Tarifa> optionalTarifa = tarifaRepository.findByDiaSemana(fecha.getDayOfWeek().getValue());

        if (optionalTarifa.isPresent()) {
            return optionalTarifa.get();
        }

        throw new NoSuchElementException("No se encontraron tarifas definidas.");
    }

    public double calcularMonto(Alquileres alq) {
        Tarifa tarifa = alq.getTarifa();
        double monto;

        double lat1 = alq.getEstacionRetiro().getLatitud();
        double lon1 = alq.getEstacionRetiro().getLongitud();
        double lat2 = alq.getEstacionDevolucion().getLatitud();
        double lon2 = alq.getEstacionDevolucion().getLongitud();
        long distanciaEnKilometros = (long) Math.floor(Math.sqrt(Math.pow(lat1 - lat2, 2) + Math.pow(lon1 - lon2, 2)) * 110);
        long diferenciaEnMinutos = Duration.between(alq.getFechaHoraRetiro(), alq.getFechaHoraDevolucion()).toMinutes();

        long horasCompletas = diferenciaEnMinutos / 60;
        long minutosRestantes = diferenciaEnMinutos % 60;

        monto = tarifa.getMontoFijoAlquiler();
        monto += distanciaEnKilometros * tarifa.getMontoKm();
        monto += horasCompletas * tarifa.getMontoHora();

        if (minutosRestantes > 30) {
            monto += tarifa.getMontoHora();
        } else {
            monto += minutosRestantes * tarifa.getMontoMinutoFraccion();
        }

        return monto;
    }
}
