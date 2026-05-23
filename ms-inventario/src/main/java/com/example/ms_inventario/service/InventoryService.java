package com.example.ms_inventario.service;

import com.example.ms_inventario.model.Inventory;
import com.example.ms_inventario.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void descontar(Long productId, Integer cantidad) {
        Inventory inv = inventoryRepository.findAll().stream()
                .filter(inventory -> inventory.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en el inventario"));

        if (inv.getStock() < cantidad) {
            throw new RuntimeException("No hay suficiente stock para realizar la venta");
        }

        inv.setStock(inv.getStock() - cantidad);
        inventoryRepository.save(inv);
    }

    public void updateStock(Long productId, Integer cantidad) {
        Inventory inv = inventoryRepository.findAll().stream()
                .filter(inventory -> inventory.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (inv == null) {
            inv = new Inventory();
            inv.setProductId(productId);
        }

        inv.setStock(cantidad);
        inventoryRepository.save(inv);
    }

    public void deleteStockByProductId(Long productId) {
        Inventory inv = inventoryRepository.findAll().stream()
                .filter(inventory -> inventory.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en el inventario"));

        inventoryRepository.delete(inv);
    }

}