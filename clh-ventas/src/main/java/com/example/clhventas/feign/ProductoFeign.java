package com.example.clhventas.feign;

import com.example.clhventas.dto.ProductoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clh-catalogo-service", path = "/producto")
public interface ProductoFeign {

    @GetMapping("/{id}")
    ProductoDto getById(@PathVariable("id") Integer id);

    @PutMapping("/{id}/reduce-stock")
    void reducirStock(@PathVariable("id") Integer id, @RequestParam("amount") Integer amount);
}
