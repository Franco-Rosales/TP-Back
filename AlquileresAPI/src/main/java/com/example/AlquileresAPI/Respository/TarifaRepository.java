package com.example.AlquileresAPI.Respository;

import com.example.AlquileresAPI.Entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    List<Tarifa> findByDefinicion(String definicion);

    Optional<Tarifa> findByDiaSemana(Integer diaSemana);
}
