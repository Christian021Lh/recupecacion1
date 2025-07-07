package com.example.clhventas.service;

import com.example.clhventas.entity.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaService {
    List<Venta> listar();

    Venta listarPorId(Integer id);

    Venta procesoVenta(Integer productoId);

    Venta editarVenta(Integer id, Venta ventaActualizada);

    void eliminarVenta(Integer id);
}
