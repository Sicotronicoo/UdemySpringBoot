package net.curso.springboot.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

import net.curso.springboot.model.Categoria;

@Service
public class CategoriasServiceImpl implements ICategoriasService {

	private List<Categoria> listaCategoria = null;

	public CategoriasServiceImpl() {
		listaCategoria = new LinkedList<Categoria>();
		
		Categoria categoria1 = new Categoria();
		categoria1.setId(1);
		categoria1.setNombre("Contabilidad");
		categoria1.setDescripcion("Descripcion de la categoria contabilidad");

		Categoria categoria2 = new Categoria();
		categoria2.setId(2);
		categoria2.setNombre("Ventas");
		categoria2.setDescripcion("Ofertas de trabajo relacionado con ventas");

		Categoria categoria3 = new Categoria();
		categoria3.setId(3);
		categoria3.setNombre("Arquitectura");
		categoria3.setDescripcion("Diseño de planos en general y trabajos relacionados");
		
		Categoria categoria4 = new Categoria();
		categoria4.setId(4);
		categoria4.setNombre("Comunicaciones");
		categoria4.setDescripcion("Trabajos relacionados con Comunicaciones");

		Categoria categoria5 = new Categoria();
		categoria5.setId(5);
		categoria5.setNombre("Educacion");
		categoria5.setDescripcion("Maestros, tutores, etc");
		
		Categoria categoria6 = new Categoria();
		categoria6.setId(6);
		categoria6.setNombre("Desarrollo de software");
		categoria6.setDescripcion("Trabajo para programadores");
		
		
		listaCategoria.add(categoria1);
		listaCategoria.add(categoria2);
		listaCategoria.add(categoria3);		
		listaCategoria.add(categoria4);
		listaCategoria.add(categoria5);
		listaCategoria.add(categoria6);

	}
	

	@Override
	public void guardar(Categoria categoria) {
		listaCategoria.add(categoria);
	}

	@Override
	public List<Categoria> buscarTodas() {
		return listaCategoria;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		for(Categoria v : listaCategoria) {
			if(v.getId()==idCategoria) {
				return v;
			}
		}
		return null;
	}


	@Override
	public void eliminar(Integer idCategoria) {
		// TODO Auto-generated method stub
		
	}
}
