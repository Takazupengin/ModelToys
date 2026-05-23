package com.example.ms_ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

@FeignClient(name = "ms-catalogo", url = "http://localhost:8084")
public interface CatalogoCliente {


    @GetMapping("/api/products")
    List<Map<String, Object>> obtenerTodosLosProductos();
}