package com.example.clhcatalogo.service.impl;

import com.example.clhcatalogo.entity.Producto;
import com.example.clhcatalogo.repository.ProductoRepository;
import com.example.clhcatalogo.service.ProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> listarPorId(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizar(Producto producto) {
        if (producto.getId() == null) {
            throw new RuntimeException("Debe proporcionar el ID del producto a actualizar.");
        }

        if (!productoRepository.existsById(producto.getId())) {
            throw new RuntimeException("El producto con ID " + producto.getId() + " no existe.");
        }

        return productoRepository.save(producto);
    }

    @Override
    public void eliminarPorId(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void reducirStock(Integer productoId, Integer amount) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no disponible."));

        if (producto.getStock() < amount) {
            throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        producto.setStock(producto.getStock() - amount);
        productoRepository.save(producto);
    }
}
