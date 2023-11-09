package com.example.EstacionesAPI.Controller;

import com.example.EstacionesAPI.Dtos.EstacionesDTO;
import com.example.EstacionesAPI.Service.EstacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionesController {
    @Autowired
    private EstacionesService estacionesService;

    @GetMapping
    public ResponseEntity<List<EstacionesDTO>> getEstacionesDisponibles(){
        List<EstacionesDTO> estacionesDTOS = estacionesService.getEstacionesDisponibles();
        return ResponseEntity.ok(estacionesDTOS);
    }
}
