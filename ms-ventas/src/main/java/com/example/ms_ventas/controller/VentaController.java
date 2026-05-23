package com.example.ms_ventas.controller;

import com.example.ms_ventas.model.Venta;
import com.example.ms_ventas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> obtenerTodasLasVentas() {
        return ResponseEntity.ok(ventaService.obtenerTodasLasVentas());
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> crearVenta(@RequestBody Map<String, Object> request) {

        Venta nuevaVenta = ventaService.crearVenta(request);


        String nombreProducto = ventaService.obtenerNombreProducto(nuevaVenta.getProductoId());


        Map<String, Object> respuestaEstetica = new java.util.LinkedHashMap<>();

        respuestaEstetica.put("mensaje", "✨ ¡Compra efectuada con éxito! ✨");
        respuestaEstetica.put("comprobanteId", "#000" + nuevaVenta.getId());
        respuestaEstetica.put("usuarioResponsable", "Usuario ID: " + nuevaVenta.getUsuarioId());
        respuestaEstetica.put("productoComprado", nombreProducto + " (ID: " + nuevaVenta.getProductoId() + ")");
        respuestaEstetica.put("cantidadUnidades", nuevaVenta.getCantidad() + " unidades");
        respuestaEstetica.put("totalAPagar", "$ " + String.format("%,.0f", nuevaVenta.getTotal()) + " CLP");
        respuestaEstetica.put("fechaOperacion", nuevaVenta.getCreadoEn().toString().substring(0, 19).replace("T", " "));

        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaEstetica);
    }
}