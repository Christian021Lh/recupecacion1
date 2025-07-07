package com.example.clhventas.feign;

import com.example.clhventas.dto.ProductoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clh-catalogo-service", path = "/producto")
public interface ProductoFeign {

    @GetMapping("/{id}")
    ProductoDto getById(@PathVariable Integer id);
}
