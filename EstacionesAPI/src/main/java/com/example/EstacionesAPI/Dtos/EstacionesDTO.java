package com.example.EstacionesAPI.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EstacionesDTO {
    private Long id;
    private String nombre;
    private LocalDate fechaHoraCracion;
    private Long latitud;
    private Long longitud;
}
