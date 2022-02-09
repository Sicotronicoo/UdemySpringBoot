package net.curso.springboot.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.curso.springboot.model.Perfil;
import net.curso.springboot.model.Usuario;
import net.curso.springboot.model.Vacante;
import net.curso.springboot.service.ICategoriasService;
import net.curso.springboot.service.IUsuariosService;
import net.curso.springboot.service.IVacantesService;

@Controller
public class HomeController {

	@Autowired 
	private ICategoriasService serviceCategorias;
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(Usuario usuario, RedirectAttributes attributes) {
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date()); 
		
		Perfil perfil = new Perfil();
		perfil.setId(3);
		usuario.agregar(perfil);
		
		serviceUsuarios.guardar(usuario);
		attributes.addFlashAttribute("msg", "Usuario registrado correctamente");

		return "redirect:/usuarios/index";
	}
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes",lista);		
		return "tabla";
	}
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		
		Vacante vacante = new Vacante();		
		vacante.setNombre("Ingeniero de comunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte a intranet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.0);
		
		model.addAttribute("vacante", vacante);
		return "detalle";
		
	}
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		
		List<String> lista = new LinkedList<String>();
		
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		model.addAttribute("empleos", lista); 
		
		return "listado"; 
	}
	@GetMapping(value = "/")
	public String mostrarHome(Model model) {
		return "home";
	}
	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("Buscando por : " +vacante);
		
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion", ExampleMatcher.GenericPropertyMatchers.contains());
		
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);
		model.addAttribute("vacantes", lista);
		return "home";
	}
	@InitBinder
	public void initBinder (WebDataBinder binder) {
		binder.registerCustomEditor(String.class,  new StringTrimmerEditor(true));
	}
	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();
		vacanteSearch.reset();
		model.addAttribute("vacantes",serviceVacantes.buscarDestacadas());		
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		model.addAttribute("search", vacanteSearch);
	}
}
