package net.curso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.curso.springboot.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer>{

}
