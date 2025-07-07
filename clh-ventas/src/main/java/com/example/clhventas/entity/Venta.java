package com.example.clhventas.entity;

import com.example.clhventas.dto.ProductoDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer productoId;
    private LocalDateTime fechaVenta;
    private Integer cantidad;
    private Double precioUnitario;
    private Double igv;
    private Double subtotal;
    private Double total;

    @Transient
    private ProductoDto productoDto;
    // Relación con detalles de venta
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles;

}

