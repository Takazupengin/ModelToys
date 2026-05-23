package com.example.ms_carrito.controller;

import com.example.ms_carrito.dto.CartItemDTO;
import com.example.ms_carrito.model.CartItem;
import com.example.ms_carrito.service.CartItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable Long userId) {
        return new ResponseEntity<>(cartItemService.getCartByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItem> addProduct(@Valid @RequestBody CartItemDTO dto) {
        log.info("Agregando producto ID {} al carrito del usuario {}", dto.getProductId(), dto.getUserId());

        CartItem item = new CartItem();
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setUserId(dto.getUserId());

        return new ResponseEntity<>(cartItemService.addToCart(item), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        log.info("Eliminando el item ID {} del carrito", id);
        cartItemService.eliminarDelCarrito(id);
        return new ResponseEntity<>("Producto eliminado del carrito exitosamente", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> actualizarCantidad(
            @PathVariable Long id,
            @RequestParam Integer cantidad) {

        log.info("Actualizando la cantidad del item ID {} a {}", id, cantidad);
        CartItem itemActualizado = cartItemService.actualizarCantidad(id, cantidad);

        return new ResponseEntity<>(itemActualizado, HttpStatus.OK);
    }
}