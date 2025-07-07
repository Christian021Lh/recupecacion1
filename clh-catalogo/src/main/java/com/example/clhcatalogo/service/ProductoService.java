package com.example.clhcatalogo.service;

import com.example.clhcatalogo.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    public List<Producto> listar();
    public Producto guardar (Producto producto);
    public Producto actualizar (Producto producto);
    public Optional <Producto> listarPorId (Integer id);
    public void eliminarPorId (Integer id);

    void reducirStock (Integer productId, Integer amount);

}
