package com.example.clhventas.controller;

import com.example.clhventas.entity.Venta;
import com.example.clhventas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    /**
     * Obtener todas las ventas registradas.
     */
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.listar();
        return ResponseEntity.ok(ventas);
    }

    /**
     * Obtener una venta por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Integer id) {
        Venta venta = ventaService.listarPorId(id);
        return ResponseEntity.ok(venta);
    }

    /**
     * Registrar una nueva venta para un producto.
     * Aquí se usa cantidad fija 1. Puedes extenderlo con cantidad personalizada si gustas.
     */
    @PostMapping("/{productoId}")
    public ResponseEntity<Venta> registrarVenta(@PathVariable Integer productoId) {
        Venta nuevaVenta = ventaService.procesoVenta(productoId);
        return ResponseEntity.ok(nuevaVenta);
    }

    /**
     * Actualizar una venta existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable Integer id, @RequestBody Venta ventaActualizada) {
        Venta venta = ventaService.editarVenta(id, ventaActualizada);
        return ResponseEntity.ok(venta);
    }

    /**
     * Eliminar una venta por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Integer id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}

