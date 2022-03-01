package net.curso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.curso.springboot.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Usuario findByUsername(String username);
}
