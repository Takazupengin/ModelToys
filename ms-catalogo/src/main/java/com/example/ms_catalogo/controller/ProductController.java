package com.example.ms_catalogo.controller;

import com.example.ms_catalogo.dto.ProductDTO;
import com.example.ms_catalogo.model.Product;
import com.example.ms_catalogo.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {

        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setGrade(productDTO.getGrade());
        newProduct.setPrice(productDTO.getPrice());

        Product savedProduct = productService.saveProduct(newProduct);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        return productService.getProductById(id)
                .map(productoExistente -> {
                    productoExistente.setName(productDTO.getName());
                    productoExistente.setGrade(productDTO.getGrade());
                    productoExistente.setPrice(productDTO.getPrice());
                    // Guardamos solo los datos comerciales en la BD de catálogo
                    Product guardado = productService.saveProduct(productoExistente);

                    // NOTA PARA LA DEFENSA: Aquí es donde a futuro se mete el FeignClient
                    // para mandarle el productDTO.getStock() al ms-inventario de forma remota.

                    return new ResponseEntity<>(guardado, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}