package net.curso.springboot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.curso.springboot.model.Solicitud;
import net.curso.springboot.model.Usuario;
import net.curso.springboot.model.Vacante;
import net.curso.springboot.service.ISolicitudesService;
import net.curso.springboot.service.IUsuariosService;
import net.curso.springboot.service.IVacantesService;
import net.curso.springboot.util.Utileria;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {

	@Value("${empleosapp.ruta.cv}")
	private String ruta;

	@Autowired
	private IUsuariosService serviceUsuarios;
	
	@Autowired
	private ISolicitudesService serviceSolicitudes;

	@Autowired
	private IVacantesService serviceVacantes;

	@GetMapping("/create/{idVacante}")
	public String crear(Solicitud solicitud, @PathVariable Integer idVacante, Model model) {
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);
		// EJERCICIO
		return "solicitudes/formSolicitud";

	}

	@PostMapping("/save")
	public String guardar(Solicitud solicitud, BindingResult result, @RequestParam("archivoCV") MultipartFile multiPart,
			HttpSession session, Authentication authentication) {
		String username = authentication.getName();

		if (result.hasErrors()) {

			System.out.println("Existieron errores");
			return "solicitudes/formSolicitud";
		}

		if (!multiPart.isEmpty()) {
			String ruta = "c:/empleos/files-cv/"; // Windows
			String nombreArchivo = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreArchivo != null) { // El archivo (CV) si se subio
				solicitud.setArchivo(nombreArchivo); // Asignamos el nombre de la imagen
			}
		}
		Usuario usuario = serviceUsuarios.buscarPorUsername(username);		
		
		solicitud.setUsuario(usuario); // Referenciamos la solicitud con el usuario 
		solicitud.setFecha(new Date());
		serviceSolicitudes.guardar(solicitud);
		return "redirect:/";
	}
	@GetMapping("/delete/{id}") 
	public String eliminar(@PathVariable Integer id) {
		serviceSolicitudes.eliminar(id);
		return "redirect:/solicitudes/index";
	}
	@GetMapping("/index") 
	public String mostrarIndexSolicitudes(Model model) {
		List<Solicitud> lista = serviceSolicitudes.buscarTodas();
		model.addAttribute("solicitudes", lista);
		return "solicitudes/listSolicitudes";
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * EJERCICIO: Declarar esta propiedad en el archivo application.properties. El
	 * valor sera el directorio en donde se guardarán los archivos de los
	 * Curriculums Vitaes de los usuarios.
	 */

	/*
	 * 
	 */

	/**
	 * Metodo que muestra la lista de solicitudes sin paginacion Seguridad: Solo
	 * disponible para un usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * 
	 * @return
	 */

	/**
	 * Metodo que muestra la lista de solicitudes con paginacion Seguridad: Solo
	 * disponible para usuarios con perfil ADMINISTRADOR/SUPERVISOR.
	 * 
	 * @return
	 */
	/*
	 * 
	 */

	/**
	 * Método para renderizar el formulario para aplicar para una Vacante Seguridad:
	 * Solo disponible para un usuario con perfil USUARIO.
	 * 
	 * @return
	 */
	/*
	 * @GetMapping("/create/{idVacante}") public String crear() {
	 * 
	 * // EJERCICIO return "solicitudes/formSolicitud";
	 * 
	 * }
	 */

	/**
	 * Método que guarda la solicitud enviada por el usuario en la base de datos
	 * Seguridad: Solo disponible para un usuario con perfil USUARIO.
	 * 
	 * @return
	 */
	/**/

	/**
	 * Método para eliminar una solicitud Seguridad: Solo disponible para usuarios
	 * con perfil ADMINISTRADOR/SUPERVISOR.
	 * 
	 * @return
	 */
	/*
	 * 
	 */

	/**
	 * Personalizamos el Data Binding para todas las propiedades de tipo Date
	 * 
	 * @param webDataBinder
	 */
	/*
	*/
}