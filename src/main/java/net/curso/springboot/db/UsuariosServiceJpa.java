package net.curso.springboot.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import net.curso.springboot.model.Usuario;
import net.curso.springboot.repository.UsuarioRepository;
import net.curso.springboot.service.IUsuariosService;

@Service
@Primary
public class UsuariosServiceJpa implements IUsuariosService {

	@Autowired
	private UsuarioRepository usuariosRepo; 
	
	@Override
	public void guardar(Usuario usuario) {
		usuariosRepo.save(usuario);
	}

	@Override
	public void eliminar(Integer idUsuario) {
		usuariosRepo.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodos() {
		return usuariosRepo.findAll();
	}

}
