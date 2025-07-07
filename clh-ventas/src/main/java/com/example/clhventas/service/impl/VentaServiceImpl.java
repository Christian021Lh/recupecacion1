package com.example.clhventas.service.impl;

import com.example.clhventas.dto.ProductoDto;
import com.example.clhventas.entity.Venta;
import com.example.clhventas.feign.ProductoFeign;
import com.example.clhventas.repository.VentaRepository;
import com.example.clhventas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoFeign productoFeign;

    @Override
    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta listarPorId(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    @Override
    public Venta procesoVenta(Integer productoId) {
        // 1. Obtener producto
        ProductoDto productoDto = productoFeign.getById(productoId);
        if (productoDto == null) {
            throw new RuntimeException("No se encontró el producto con ID: " + productoId);
        }

        // 2. Validar stock disponible
        if (productoDto.getCantidad() < 1) {
            throw new RuntimeException("No hay stock disponible del producto con ID: " + productoId);
        }

        // 3. Reducir el stock
        productoFeign.reducirStock(productoId, 1);

        // 4. Calcular precios
        Double precioUnitario = productoDto.getPrecio();
        Double igv = precioUnitario * 0.18;
        Double total = precioUnitario + igv;

        // 5. Crear venta
        Venta venta = new Venta();
        venta.setProductoId(productoId);
        venta.setCantidad(1);
        venta.setPrecioUnitario(precioUnitario);
        venta.setSubtotal(precioUnitario);
        venta.setIgv(igv);
        venta.setTotal(total);
        venta.setFechaVenta(LocalDateTime.now());

        // 6. Guardar venta
        return ventaRepository.save(venta);
    }

    @Override
    public Venta editarVenta(Integer id, Venta ventaActualizada) {
        Venta ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        ventaExistente.setCantidad(ventaActualizada.getCantidad());
        ventaExistente.setPrecioUnitario(ventaActualizada.getPrecioUnitario());
        ventaExistente.setSubtotal(ventaActualizada.getSubtotal());
        ventaExistente.setIgv(ventaActualizada.getIgv());
        ventaExistente.setTotal(ventaActualizada.getTotal());
        ventaExistente.setFechaVenta(ventaActualizada.getFechaVenta());

        return ventaRepository.save(ventaExistente);
    }

    @Override
    public void eliminarVenta(Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        ventaRepository.delete(venta);
    }
}
