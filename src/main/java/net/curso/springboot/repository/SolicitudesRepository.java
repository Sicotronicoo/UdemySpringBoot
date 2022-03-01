package net.curso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.curso.springboot.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}

