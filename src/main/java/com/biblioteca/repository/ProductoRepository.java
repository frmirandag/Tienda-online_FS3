package com.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

