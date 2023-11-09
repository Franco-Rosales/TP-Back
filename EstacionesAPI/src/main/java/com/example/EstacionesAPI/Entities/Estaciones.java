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
    private Double latitud;
    private Double longitud;

    public double calcularDistancia(double latitudActual, double longitudActual) {
        // Calcula la diferencia en latitud y longitud.
        double deltaLatitud = latitud - latitudActual;
        double deltaLongitud = longitud - longitudActual;

        // Calcula la distancia euclidiana en metros.
        return Math.sqrt(Math.pow(deltaLatitud * 110000, 2) + Math.pow(deltaLongitud * 110000, 2));
    }
}
