package net.curso.springboot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.curso.springboot.model.Vacante;
import net.curso.springboot.service.ICategoriasService;
import net.curso.springboot.service.IVacantesService;
import net.curso.springboot.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class vacantesController {

	@Value("${springboot.ruta.imagenes}")
	private String ruta;

	@Autowired
	private IVacantesService serviceVacantes;

	@Autowired
	// @Qualifier("categoriasServiceJpa")
	private ICategoriasService serviceCategorias;

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
		model.addAttribute("vacante", lista);
		return "vacantes/listVacantes";
	}

	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		Page<Vacante> lista = serviceVacantes.buscarTodas(page);
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}

	@GetMapping("/create")
	public String crear(Vacante vacante) {
		return "vacantes/formVacante";
	}

	@PostMapping("/save")
	private String guardar(@RequestParam("archivoImagen") MultipartFile multiPart, Vacante vacante,
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());
			}
			return "vacantes/formVacante";
		}
		if (!multiPart.isEmpty()) {
			// String ruta = "c:/empleos/img-vacantes/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				vacante.setImagen(nombreImagen);
			}
		}
		serviceVacantes.guardar(vacante);
		attributes.addFlashAttribute("msg", "Registro guardado");
		System.out.println("Vacante: " + vacante);
		return "redirect:/vacantes/index";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {
		System.out.println("Borrando vacante con id: : " + idVacante);
		serviceVacantes.eliminar(idVacante);
		attributes.addFlashAttribute("msg", "La vacante fue eliminada");
		return "redirect:/vacantes/index";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		return "vacantes/formVacante";
	}

	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {

		Vacante vacante = serviceVacantes.buscarPorId(idVacante);

		System.out.println("Vacante: " + vacante);
		model.addAttribute("vacante", vacante);

		return "detalle";
	}

	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
}
