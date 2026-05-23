package com.example.ms_carrito.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "Debes agregar al menos 1 producto al carrito")
    private Integer quantity;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;
}