package com.example.EstacionesAPI.Respository;

import com.example.EstacionesAPI.Entities.Estaciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacionesRepository extends JpaRepository<Estaciones,Long> {
}
