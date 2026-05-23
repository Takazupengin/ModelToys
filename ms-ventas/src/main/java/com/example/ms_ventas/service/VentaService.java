package com.example.ms_ventas.service;

import com.example.ms_ventas.client.CatalogoCliente;
import com.example.ms_ventas.client.InventarioCliente;
import com.example.ms_ventas.model.Venta;
import com.example.ms_ventas.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private InventarioCliente inventarioCliente;

    @Autowired
    private CatalogoCliente catalogoCliente;

    public List<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public Venta crearVenta(Map<String, Object> request) {

        Long prodId = Long.parseLong(request.get("productId").toString());
        Integer cant = Integer.parseInt(request.get("quantity").toString());
        Long userId = Long.parseLong(request.get("userId").toString());


        List<Map<String, Object>> productos = catalogoCliente.obtenerTodosLosProductos();


        Map<String, Object> productoEncontrado = productos.stream()
                .filter(p -> Long.parseLong(p.get("id").toString()) == prodId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El producto con ID " + prodId + " no existe en el catálogo"));


        Double precioUnitario = Double.parseDouble(productoEncontrado.get("price").toString());


        Double totalCalculado = precioUnitario * cant;


        Venta venta = new Venta();
        venta.setUsuarioId(userId);
        venta.setProductoId(prodId);
        venta.setCantidad(cant);
        venta.setTotal(totalCalculado);
        venta.setCreadoEn(LocalDateTime.now());


        Map<String, Object> datosInventario = new HashMap<>();
        datosInventario.put("productId", prodId);
        datosInventario.put("stock", cant);

        inventarioCliente.descontarStock(datosInventario);


        return ventaRepository.save(venta);
    }


    public String obtenerNombreProducto(Long id) {
        try {
            List<Map<String, Object>> productos = catalogoCliente.obtenerTodosLosProductos();
            return productos.stream()
                    .filter(p -> Long.parseLong(p.get("id").toString()) == id)
                    .map(p -> p.get("name").toString())
                    .findFirst()
                    .orElse("Producto #" + id);
        } catch (Exception e) {
            return "Producto #" + id;
        }
    }
}