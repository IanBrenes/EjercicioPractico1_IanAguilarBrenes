/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.controllers;

import EjercicioPractico1.demo.domain.Sugerencia;
import EjercicioPractico1.demo.service.SugerenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/sugerencias")
public class SugerenciaController {

    @Autowired
    private SugerenciaService sugerenciaService;

    @GetMapping("/")
    public String listarSugerencias(Model model) {
        List<Sugerencia> sugerencias = sugerenciaService.listarSugerenciasOrdenadasPorFecha();
        Long totalQuejas = sugerenciaService.contarQuejas();
        Long totalSugerencias = sugerenciaService.contarSugerencias();

        model.addAttribute("sugerencias", sugerencias);
        model.addAttribute("totalQuejas", totalQuejas);
        model.addAttribute("totalSugerencias", totalSugerencias);
        model.addAttribute("titulo", "Quejas y Sugerencias");
        return "sugerencias/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("sugerencia", new Sugerencia());
        model.addAttribute("titulo", "Nueva Queja o Sugerencia");
        return "sugerencias/formulario";
    }

    @PostMapping("/guardar")
    public String guardarSugerencia(@Valid @ModelAttribute Sugerencia sugerencia,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nueva Queja o Sugerencia");
            return "sugerencias/formulario";
        }

        if (!sugerenciaService.validarTipoSugerencia(sugerencia.getTipo())) {
            model.addAttribute("error", "Tipo de sugerencia inválido");
            model.addAttribute("titulo", "Nueva Queja o Sugerencia");
            return "sugerencias/formulario";
        }

        try {
            sugerenciaService.guardarSugerencia(sugerencia);
            redirectAttributes.addFlashAttribute("mensaje", "Su " + sugerencia.getTipo().toLowerCase() + " ha sido enviada exitosamente. Gracias por contactarnos.");
            return "redirect:/sugerencias/nueva";
        } catch (Exception e) {
            model.addAttribute("error", "Error al enviar la sugerencia: " + e.getMessage());
            model.addAttribute("titulo", "Nueva Queja o Sugerencia");
            return "sugerencias/formulario";
        }
    }

    @GetMapping("/ver/{id}")
    public String verSugerencia(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Sugerencia sugerencia = sugerenciaService.obtenerSugerenciaPorId(id);

        if (sugerencia == null) {
            redirectAttributes.addFlashAttribute("error", "Sugerencia no encontrada");
            return "redirect:/sugerencias/";
        }

        model.addAttribute("sugerencia", sugerencia);
        model.addAttribute("titulo", "Detalle de " + sugerencia.getTipo());
        return "sugerencias/detalle";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSugerencia(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (!sugerenciaService.existeSugerenciaPorId(id)) {
                redirectAttributes.addFlashAttribute("error", "Sugerencia no encontrada");
                return "redirect:/sugerencias/";
            }

            sugerenciaService.eliminarSugerencia(id);
            redirectAttributes.addFlashAttribute("mensaje", "Sugerencia eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la sugerencia: " + e.getMessage());
        }

        return "redirect:/sugerencias/";
    }

    @GetMapping("/filtrar")
    public String filtrarSugerencias(@RequestParam(required = false) String tipo, Model model) {
        List<Sugerencia> sugerencias;

        if (tipo != null && !tipo.trim().isEmpty()) {
            sugerencias = sugerenciaService.buscarSugerenciasPorTipo(tipo);
            model.addAttribute("filtroTipo", tipo);
        } else {
            sugerencias = sugerenciaService.listarSugerenciasOrdenadasPorFecha();
        }

        Long totalQuejas = sugerenciaService.contarQuejas();
        Long totalSugerencias = sugerenciaService.contarSugerencias();

        model.addAttribute("sugerencias", sugerencias);
        model.addAttribute("totalQuejas", totalQuejas);
        model.addAttribute("totalSugerencias", totalSugerencias);
        model.addAttribute("titulo", "Quejas y Sugerencias");
        return "sugerencias/lista";
    }
}
