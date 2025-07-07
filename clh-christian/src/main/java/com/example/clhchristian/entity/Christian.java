package com.example.clhchristian.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface Christian {
    package com.example.clhchristian.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

    @Entity
    @Table(name = "persona")
    @Data
    @NoArgsConstructor
    public class Persona {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        // Nombre opcional
        private String nombre;

        // Fecha de nacimiento
        @Column(nullable = false)
        private LocalDate fechaNacimiento;

        /**
         * Calcula la edad actual basado en fechaNacimiento.
         * No se mapea en la base de datos.
         */
        @Transient
        public int getEdad() {
            return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
        }
    }

}
