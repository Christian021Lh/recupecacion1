package com.example.clhcatalogo.repository;

import com.example.clhcatalogo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {


}
