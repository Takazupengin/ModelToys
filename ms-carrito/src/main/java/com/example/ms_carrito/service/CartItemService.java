package com.example.ms_carrito.service;

import com.example.ms_carrito.model.CartItem;
import com.example.ms_carrito.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getCartByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addToCart(CartItem item) {
        return cartItemRepository.save(item);
    }

    public void eliminarDelCarrito(Long id) {
        cartItemRepository.deleteById(id);
    }

    public CartItem actualizarCantidad(Long id, Integer nuevaCantidad) {
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El item no existe en el carrito"));
        item.setQuantity(nuevaCantidad);
        return cartItemRepository.save(item);
    }
}