package com.example.clhcatalogo.controller;

import com.example.clhcatalogo.entity.Producto;
import com.example.clhcatalogo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoContoller {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> list() {
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable Integer id) {
        Optional<Producto> productoOpt = productoService.listarPorId(id);
        return productoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.guardar(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@RequestBody Producto producto, @PathVariable Integer id) {
        producto.setId(id); // Asegura que el ID venga de la URL
        return ResponseEntity.ok(productoService.actualizar(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        productoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reduce-stock")
    public ResponseEntity<Void> reduceStock(@PathVariable Integer id, @RequestParam Integer amount) {
        productoService.reducirStock(id, amount);
        return ResponseEntity.ok().build();
    }

}
