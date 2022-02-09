package net.curso.springboot.repository;

import org.springframework.data.repository.CrudRepository;

import net.curso.springboot.model.Usuario;

public interface UsuariosRepository extends CrudRepository<Usuario, Integer> {

}
