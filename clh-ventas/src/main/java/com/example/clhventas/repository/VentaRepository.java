package com.example.clhventas.repository;

import com.example.clhventas.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Integer > {
}
