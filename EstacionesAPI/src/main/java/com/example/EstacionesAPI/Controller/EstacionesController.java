package com.example.EstacionesAPI.Controller;

import com.example.EstacionesAPI.Dtos.EstacionesDTO;
import com.example.EstacionesAPI.EstacionesApiApplication;
import com.example.EstacionesAPI.Service.EstacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping ("/estacion-cercana")
    //se prueba con GET /estacion-cercana?latitud=XX.XXXXX&longitud=YY.YYYYY
    public ResponseEntity<EstacionesDTO> getEstacionMasCercana(@RequestParam("latitud") double latitud, @RequestParam("longitud") double longitud){
        EstacionesDTO estacionDTO = estacionesService.getEstacionMasCercana(latitud,longitud);
        return ResponseEntity.ok(estacionDTO);
    }
    @PostMapping()
    public ResponseEntity<EstacionesDTO> agregarEstacion(@RequestBody EstacionesDTO estacionesDTO){
        EstacionesDTO agregada = estacionesService.addEstacion(estacionesDTO);
        return ResponseEntity.ok(agregada);
    }
}
