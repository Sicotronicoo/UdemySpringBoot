package net.curso.springboot.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.curso.springboot.model.Vacante;
import net.curso.springboot.repository.VacantesRepository;
import net.curso.springboot.service.IVacantesService;

@Service
@Primary
public class VacanteServiceJpa implements IVacantesService {

	@Autowired
	private VacantesRepository vacanteRepo;

	@Override
	public List<Vacante> buscarTodas() {
		return vacanteRepo.findAll();
	}

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		Optional<Vacante> optional = vacanteRepo.findById(idVacante);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		vacanteRepo.save(vacante);		
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		return vacanteRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Creada");
	}

	@Override
	public void eliminar(Integer idVacante) {
		vacanteRepo.deleteById(idVacante);
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		
		return vacanteRepo.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		return vacanteRepo.findAll(page);
	}

}
