package net.curso.springboot.service;

import java.util.List;

import net.curso.springboot.model.Usuario;
 
public interface IUsuariosService {
	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	List<Usuario> buscarTodos();
	Usuario buscarPorUsername(String username);
}
