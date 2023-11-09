package com.example.EstacionesAPI.Service;

import com.example.EstacionesAPI.Dtos.EstacionesDTO;
import com.example.EstacionesAPI.Entities.Estaciones;
import com.example.EstacionesAPI.Respository.EstacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private EstacionesDTO transformarADTO(Estaciones estacion) {
        EstacionesDTO estacionesDTO = new EstacionesDTO();
        estacionesDTO.setId(estacion.getId());
        estacionesDTO.setLatitud(estacion.getLatitud());
        estacionesDTO.setNombre(estacion.getNombre());
        estacionesDTO.setLongitud(estacion.getLongitud());
        estacionesDTO.setFechaHoraCracion(estacion.getFechaHoraCreacion());
        return estacionesDTO;
    }
}
