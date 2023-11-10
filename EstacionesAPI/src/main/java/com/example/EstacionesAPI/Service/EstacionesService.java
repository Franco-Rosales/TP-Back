package com.example.EstacionesAPI.Service;

import com.example.EstacionesAPI.Dtos.EstacionesDTO;
import com.example.EstacionesAPI.Entities.Estaciones;
import com.example.EstacionesAPI.Respository.EstacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionesService {
    @Autowired
    EstacionesRepository estacionesRepository;
    public List<EstacionesDTO> getEstacionesDisponibles() {
        List <Estaciones> estacionesList = estacionesRepository.findAll();
        List<EstacionesDTO> estacionesDTOS = estacionesList.stream().map(this::transformarADTO).collect(Collectors.toList());
        return estacionesDTOS;
    }
    public EstacionesDTO getEstacionMasCercana(double latitud, double longitud) {
        List<Estaciones> estacionesList = estacionesRepository.findAll();
        Estaciones estacionMasCercana = encontrarEstacionMasCercana(estacionesList,latitud,longitud);
        return transformarADTO(estacionMasCercana);

    }
    public EstacionesDTO addEstacion(EstacionesDTO estacionesDTO) {
        Estaciones estacion = estacionesRepository.save(transformarAEntidad(estacionesDTO));
        return transformarADTO(estacion);
    }

    public Estaciones getById(Long id ) {
        Estaciones estaciones = estacionesRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontro la estacion"));
        return estaciones;

    }

    private EstacionesDTO transformarADTO(Estaciones estacion) {
        EstacionesDTO estacionesDTO = new EstacionesDTO();
        estacionesDTO.setId(estacion.getId());
        estacionesDTO.setLatitud(estacion.getLatitud());
        estacionesDTO.setNombre(estacion.getNombre());
        estacionesDTO.setLongitud(estacion.getLongitud());
        estacionesDTO.setFechaHoraCracion(estacion.getFechaHoraCreacion());
        return estacionesDTO;
    }
    private Estaciones transformarAEntidad(EstacionesDTO estacionesDTO) {
        Estaciones estacion = new Estaciones();
        estacion.setNombre(estacionesDTO.getNombre());
        estacion.setLongitud(estacionesDTO.getLongitud());
        if(estacionesDTO.getFechaHoraCracion() == null){
            estacion.setFechaHoraCreacion(LocalDate.now());
        }
        else {
            estacion.setFechaHoraCreacion(estacionesDTO.getFechaHoraCracion());
        }
        estacion.setLatitud(estacionesDTO.getLatitud());
        return estacion;
    }
    public static Estaciones encontrarEstacionMasCercana(List<Estaciones> estaciones, double latitudActual, double longitudActual) {
        Estaciones estacionMasCercana = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (Estaciones estacion : estaciones) {
            double distancia = estacion.calcularDistancia(latitudActual, longitudActual);

            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                estacionMasCercana = estacion;
            }
        }
        return estacionMasCercana;
    }
}
