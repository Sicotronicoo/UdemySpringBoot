package net.curso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.curso.springboot.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
