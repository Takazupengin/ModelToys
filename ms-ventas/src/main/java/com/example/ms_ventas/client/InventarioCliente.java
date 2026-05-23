package com.example.ms_ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "ms-inventario", url = "http://localhost:8081")
public interface InventarioCliente {

    @PostMapping("/api/inventory/descontar")
    void descontarStock(@RequestBody Map<String, Object> request);
}