package com.example.AlquileresAPI.Controller;

import com.example.AlquileresAPI.Entities.Alquileres;
import com.example.AlquileresAPI.Service.AlquileresServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")

public class AlquileresController {
    @Autowired
    private AlquileresServices alquileresServices;

    @GetMapping
    public ResponseEntity<List<Alquileres>> getAlquileres(@RequestParam (name = "monto",required = false) double monto){
        List<Alquileres> alquileres = alquileresServices.getAlquileresFinalizados(monto);
        return ResponseEntity.ok(alquileres);
    }

}
