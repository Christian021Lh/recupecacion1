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

    /**
     * Lista todas las ventas en la base de datos.
     */
    @Override
    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    /**
     * Busca una venta por su ID.
     *
     * @param id ID de la venta
     * @return Venta encontrada
     * @throws RuntimeException si no existe
     */
    @Override
    public Venta listarPorId(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    /**
     * Crea una nueva venta de un producto.
     *
     * @param productoId ID del producto a vender
     * @return Venta registrada
     */
    @Override
    public Venta procesoVenta(Integer productoId) {
        // Llamar al microservicio de productos vía Feign
        ProductoDto productoDto = productoFeign.getById(productoId);
        if (productoDto == null) {
            throw new RuntimeException("No se encontró el producto con ID: " + productoId);
        }

        // Obtener precio unitario del producto
        Double precioUnitario = productoDto.getPrecio();

        // Calcular IGV y total
        Double igv = precioUnitario * 0.18;
        Double total = precioUnitario + igv;

        // Crear la entidad Venta
        Venta venta = new Venta();
        venta.setProductoId(productoId);
        venta.setCantidad(1); // Puedes parametrizar la cantidad si gustas
        venta.setPrecioUnitario(precioUnitario);
        venta.setSubtotal(precioUnitario);
        venta.setIgv(igv);
        venta.setTotal(total);
        venta.setFechaVenta(LocalDateTime.now());

        // Guardar en base de datos
        return ventaRepository.save(venta);
    }

    /**
     * Edita una venta existente.
     *
     * @param id ID de la venta a editar
     * @param ventaActualizada Datos actualizados
     * @return Venta actualizada
     */
    @Override
    public Venta editarVenta(Integer id, Venta ventaActualizada) {
        // Buscar la venta existente
        Venta ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        // Actualizar campos
        ventaExistente.setCantidad(ventaActualizada.getCantidad());
        ventaExistente.setPrecioUnitario(ventaActualizada.getPrecioUnitario());
        ventaExistente.setSubtotal(ventaActualizada.getSubtotal());
        ventaExistente.setIgv(ventaActualizada.getIgv());
        ventaExistente.setTotal(ventaActualizada.getTotal());
        ventaExistente.setFechaVenta(ventaActualizada.getFechaVenta());

        // Guardar cambios
        return ventaRepository.save(ventaExistente);
    }

    /**
     * Elimina una venta por su ID.
     *
     * @param id ID de la venta
     */
    @Override
    public void eliminarVenta(Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        ventaRepository.delete(venta);
    }
}
