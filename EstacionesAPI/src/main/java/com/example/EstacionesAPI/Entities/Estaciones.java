package com.example.EstacionesAPI.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Estaciones")
public class Estaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(name = "FECHA_HORA_CREACION")
    private LocalDate fechaHoraCreacion;
    private Long latitud;
    private Long longitud;
}
