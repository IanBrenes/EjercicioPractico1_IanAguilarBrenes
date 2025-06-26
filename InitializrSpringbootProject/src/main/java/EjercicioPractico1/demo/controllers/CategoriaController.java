/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.controllers;

import EjercicioPractico1.demo.domain.Categoria;
import EjercicioPractico1.demo.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/")
    public String listarCategorias(Model model) {
        List<Categoria> categorias = categoriaService.listarCategoriasOrdenadas();
        model.addAttribute("categorias", categorias);
        model.addAttribute("titulo", "Gestión de Categorías");
        return "categorias/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("titulo", "Nueva Categoría");
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@Valid @ModelAttribute Categoria categoria,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", categoria.getIdCategoria() != null ? "Editar Categoría" : "Nueva Categoría");
            return "categorias/formulario";
        }

        // Validar que no exista otra categoría con el mismo nombre
        if (categoria.getIdCategoria() == null && categoriaService.existeCategoriaPorNombre(categoria.getNombre())) {
            model.addAttribute("error", "Ya existe una categoría con ese nombre");
            model.addAttribute("titulo", "Nueva Categoría");
            return "categorias/formulario";
        }

        try {
            categoriaService.guardarCategoria(categoria);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría guardada exitosamente");
            return "redirect:/categorias/";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la categoría: " + e.getMessage());
            model.addAttribute("titulo", categoria.getIdCategoria() != null ? "Editar Categoría" : "Nueva Categoría");
            return "categorias/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);

        if (categoria == null) {
            redirectAttributes.addFlashAttribute("error", "Categoría no encontrada");
            return "redirect:/categorias/";
        }

        model.addAttribute("categoria", categoria);
        model.addAttribute("titulo", "Editar Categoría");
        return "categorias/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (!categoriaService.existeCategoriaPorId(id)) {
                redirectAttributes.addFlashAttribute("error", "Categoría no encontrada");
                return "redirect:/categorias/";
            }

            categoriaService.eliminarCategoria(id);
            redirectAttributes.addFlashAttribute("mensaje", "Categoría eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar la categoría porque tiene productos asociados");
        }

        return "redirect:/categorias/";
    }

    @GetMapping("/buscar")
    public String buscarCategorias(@RequestParam(required = false) String nombre, Model model) {
        List<Categoria> categorias;

        if (nombre != null && !nombre.trim().isEmpty()) {
            categorias = categoriaService.buscarCategoriasPorNombre(nombre);
            model.addAttribute("busqueda", nombre);
        } else {
            categorias = categoriaService.listarCategoriasOrdenadas();
        }

        model.addAttribute("categorias", categorias);
        model.addAttribute("titulo", "Buscar Categorías");
        return "categorias/lista";
    }
}
