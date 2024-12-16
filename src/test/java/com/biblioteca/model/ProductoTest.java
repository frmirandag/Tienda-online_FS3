package com.biblioteca.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        // Inicializamos el validador
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testProductoValido() {
        Producto producto = new Producto();
        producto.setNombre("Producto de prueba");
        producto.setDescripcion("Descripción del producto");
        producto.setPrecio(100);
        producto.setStock(10);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        assertTrue(violations.isEmpty(), "El producto debería ser válido.");
    }

    @Test
    public void testNombreNulo() {
        Producto producto = new Producto();
        producto.setDescripcion("Descripción del producto");
        producto.setPrecio(100);
        producto.setStock(10);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        assertFalse(violations.isEmpty(), "El producto debería ser inválido debido a nombre nulo.");
        assertEquals(1, violations.size(), "Debería haber una violación.");
        assertEquals("El nombre no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testDescripcionNula() {
        Producto producto = new Producto();
        producto.setNombre("Producto de prueba");
        producto.setPrecio(100);
        producto.setStock(10);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        assertFalse(violations.isEmpty(), "El producto debería ser inválido debido a descripción nula.");
        assertEquals(1, violations.size(), "Debería haber una violación.");
        assertEquals("La descripción no puede ser nula", violations.iterator().next().getMessage());
    }

    @Test
    public void testPrecioNegativo() {
        Producto producto = new Producto();
        producto.setNombre("Producto de prueba");
        producto.setDescripcion("Descripción del producto");
        producto.setPrecio(-10);  // Precio negativo
        producto.setStock(10);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        assertFalse(violations.isEmpty(), "El producto debería ser inválido debido al precio negativo.");
        assertEquals(1, violations.size(), "Debería haber una violación.");
        assertEquals("El precio no puede ser menor a 0", violations.iterator().next().getMessage());
    }

    @Test
    public void testStockNegativo() {
        Producto producto = new Producto();
        producto.setNombre("Producto de prueba");
        producto.setDescripcion("Descripción del producto");
        producto.setPrecio(100);
        producto.setStock(-5);  // Stock negativo

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        assertFalse(violations.isEmpty(), "El producto debería ser inválido debido al stock negativo.");
        assertEquals(1, violations.size(), "Debería haber una violación.");
        assertEquals("El stock no puede ser menor a 0", violations.iterator().next().getMessage());
    }

    @Test
    public void testNombreSize() {
        Producto producto = new Producto();
        producto.setNombre("");  // Nombre vacío
        producto.setDescripcion("Descripción del producto");
        producto.setPrecio(100);
        producto.setStock(10);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        assertFalse(violations.isEmpty(), "El producto debería ser inválido debido a un nombre vacío.");
        assertEquals(1, violations.size(), "Debería haber una violación.");
        assertEquals("El nombre debe tener entre 1 y 100 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    public void testDescripcionSize() {
        Producto producto = new Producto();
        producto.setNombre("Producto de prueba");
        producto.setDescripcion("");  // Descripción vacía
        producto.setPrecio(100);
        producto.setStock(10);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);

        assertFalse(violations.isEmpty(), "El producto debería ser inválido debido a una descripción vacía.");
        assertEquals(1, violations.size(), "Debería haber una violación.");
        assertEquals("La descripción debe tener entre 1 y 255 caracteres", violations.iterator().next().getMessage());
    }
}
