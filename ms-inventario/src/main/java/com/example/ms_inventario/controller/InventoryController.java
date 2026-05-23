package com.example.ms_inventario.controller;

import com.example.ms_inventario.model.Inventory;
import com.example.ms_inventario.service.InventoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory") // 👈 Revertido a tu inglés original
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAll() {
        return inventoryService.getAll();
    }

    @PostMapping
    public Inventory create(@RequestBody Inventory inventory) {
        return inventoryService.save(inventory);
    }


    @PutMapping
    public ResponseEntity<?> actualizarStockInLive(@Valid @RequestBody Inventory inventory) {

        log.info("Actualizando inventario para el producto ID: {}. Nueva cantidad: {}",
                inventory.getProductId(), inventory.getStock());


        inventoryService.updateStock(inventory.getProductId(), inventory.getStock());

        return ResponseEntity.ok().body("{\"mensaje\": \"Stock actualizado con éxito en inventario\"}");
    }


    @PostMapping("/descontar")
    public ResponseEntity<String> descontarStock(@RequestBody Inventory request) {

        inventoryService.descontar(request.getProductId(), request.getStock());

        return new ResponseEntity<>("Stock actualizado con éxito", HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> eliminarInventario(@PathVariable Long productId) {

        log.info("Eliminando por completo el inventario del producto ID: {}", productId);

        inventoryService.deleteStockByProductId(productId);

        return ResponseEntity.ok().body("{\"mensaje\": \"Inventario eliminado con éxito\"}");
    }
}