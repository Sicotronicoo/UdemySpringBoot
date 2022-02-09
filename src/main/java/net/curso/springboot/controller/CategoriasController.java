package net.curso.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.curso.springboot.model.Categoria;
import net.curso.springboot.service.ICategoriasService;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {

	@Autowired
	//@Qualifier("categoriasServiceJpa")
	private ICategoriasService serviceCategorias;

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Categoria> listaCategoria = serviceCategorias.buscarTodas();
		model.addAttribute("categorias", listaCategoria);
		return "categorias/listCategorias";
	}

	@GetMapping("/create")
	public String crear(Categoria categoria) {
		return "categorias/formCategorias";
	}

	@PostMapping("/save")
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()){		
			System.out.println("Existieron errores");
			return "categorias/formCategoria";
		}
		serviceCategorias.guardar(categoria);
		attributes.addFlashAttribute("msg", "Los datos de la categoría fueron guardados!");			
		return "redirect:/categorias/index";
	}
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCategoria, Model model) {
		Categoria categoria = serviceCategorias.buscarPorId(idCategoria);
		model.addAttribute("categoria", categoria);
		return"categorias/formCategorias";
	}
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCategoria, RedirectAttributes attributes) {
		System.out.println("Borrando categoria con id: : " + idCategoria);
		serviceCategorias.eliminar(idCategoria);
		attributes.addFlashAttribute("msg","La categoria fue eliminada");
		return "redirect:/categorias/index";
	}
}
