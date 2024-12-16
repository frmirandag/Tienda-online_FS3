package com.biblioteca.controller;

import com.biblioteca.model.Producto;
import com.biblioteca.service.ProductoService;
import com.biblioteca.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private Producto producto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto A");
        producto.setDescripcion("Descripción del Producto A");
        producto.setPrecio(100.50);
        producto.setStock(10);
    }

    // Test para listar productos
    @SuppressWarnings("null")
    @Test
    public void testListarProductos() {
        List<Producto> productos = List.of(producto);
        when(productoService.listarProductos()).thenReturn(productos);

        ResponseEntity<List<Producto>> response = productoController.listarProductos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(productoService, times(1)).listarProductos();
    }

    // Test para crear producto
    @SuppressWarnings("null")
    @Test
    public void testCrearProducto() {
        when(productoService.guardarProducto(any(Producto.class))).thenReturn(producto);

        ResponseEntity<Producto> response = productoController.crearProducto(producto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Producto A", response.getBody().getNombre());
        verify(productoService, times(1)).guardarProducto(any(Producto.class));
    }

    // Test para obtener producto por ID
    @SuppressWarnings("null")
    @Test
    public void testObtenerProducto() {
        when(productoService.obtenerProducto(1L)).thenReturn(Optional.of(producto));

        ResponseEntity<Producto> response = productoController.obtenerProducto(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto A", response.getBody().getNombre());
        verify(productoService, times(1)).obtenerProducto(1L);
    }

    // Test para obtener producto por ID (producto no encontrado)
    @Test
    public void testObtenerProductoNoEncontrado() {
        when(productoService.obtenerProducto(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productoController.obtenerProducto(1L);
        });

        assertEquals("El producto con ID 1 no fue encontrado.", exception.getMessage());
    }

    // Test para actualizar producto
    @SuppressWarnings("null")
    @Test
    public void testActualizarProducto() {
        Producto productoActualizado = new Producto();
        productoActualizado.setId(1L);
        productoActualizado.setNombre("Producto A Actualizado");
        productoActualizado.setDescripcion("Descripción actualizada");
        productoActualizado.setPrecio(150.00);
        productoActualizado.setStock(20);

        when(productoService.obtenerProducto(1L)).thenReturn(Optional.of(producto));
        when(productoService.guardarProducto(any(Producto.class))).thenReturn(productoActualizado);

        ResponseEntity<Producto> response = productoController.actualizarProducto(1L, productoActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto A Actualizado", response.getBody().getNombre());
        verify(productoService, times(1)).guardarProducto(any(Producto.class));
    }

    // Test para eliminar producto
    @Test
    public void testEliminarProducto() {
        when(productoService.obtenerProducto(1L)).thenReturn(Optional.of(producto));

        ResponseEntity<Void> response = productoController.eliminarProducto(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productoService, times(1)).eliminarProducto(1L);
    }

    // Test para eliminar producto (producto no encontrado)
    @Test
    public void testEliminarProductoNoEncontrado() {
        when(productoService.obtenerProducto(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productoController.eliminarProducto(1L);
        });

        assertEquals("El producto con ID 1 no fue encontrado.", exception.getMessage());
    }
}
