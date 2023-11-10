package com.example.AlquileresAPI.Respository;

import com.example.AlquileresAPI.Entities.Alquileres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquileresRepository extends  JpaRepository<Alquileres,Long>{
    @Query("SELECT a FROM Alquileres a WHERE a.estado = ?1 AND a.idCliente = ?2")
    Alquileres findByEstadoAndIdCliente(Integer estado, String idCliente);
}
