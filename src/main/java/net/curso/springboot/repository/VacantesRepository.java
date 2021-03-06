package net.curso.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.curso.springboot.model.Vacante;

public interface VacantesRepository extends JpaRepository<Vacante, Integer> {

	List<Vacante> findByEstatus(String estatus);
	List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(Integer destacado, String estatus);
	List<Vacante> findBySalarioBetweenOrderBySalarioDesc(double s1, double s2);
	List<Vacante> findByEstatusIn(String[] estatus);
}
