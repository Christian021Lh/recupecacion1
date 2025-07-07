package com.example.clhchristian.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/christian")
public class ChristianController {

    @GetMapping("/edad/{anioNacimiento}")
    public ResponseEntity<Map<String, Object>> calcularEdad(@PathVariable int anioNacimiento) {
        int anioActual = LocalDate.now().getYear();
        int edad = anioActual - anioNacimiento;

        if (edad < 0) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "El año de nacimiento no puede ser mayor que el año actual.")
            );
        }

        return ResponseEntity.ok(
                Map.of(
                        "anioNacimiento", anioNacimiento,
                        "anioActual", anioActual,
                        "edad", edad
                )
        );
    }
}

