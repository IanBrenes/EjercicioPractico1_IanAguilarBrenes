/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.controllers;

import EjercicioPractico1.demo.domain.Producto;
import EjercicioPractico1.demo.domain.Categoria;
import EjercicioPractico1.demo.service.ProductoService;
import EjercicioPractico1.demo.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/")
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.listarProductosOrdenados();
        List<Categoria> categorias = categoriaService.listarCategoriasOrdenadas();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("titulo", "Gestión de Productos");
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        List<Categoria> categorias = categoriaService.listarCategoriasOrdenadas();

        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categorias);
        model.addAttribute("titulo", "Nuevo Producto");
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            List<Categoria> categorias = categoriaService.listarCategoriasOrdenadas();
            model.addAttribute("categorias", categorias);
            model.addAttribute("titulo", producto.getIdProducto() != null ? "Editar Producto" : "Nuevo Producto");
            return "productos/formulario";
        }

        try {
            productoService.guardarProducto(producto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto guardado exitosamente");
            return "redirect:/productos/";
        } catch (Exception e) {
            List<Categoria> categorias = categoriaService.listarCategoriasOrdenadas();
            model.addAttribute("categorias", categorias);
            model.addAttribute("error", "Error al guardar el producto: " + e.getMessage());
            model.addAttribute("titulo", producto.getIdProducto() != null ? "Editar Producto" : "Nuevo Producto");
            return "productos/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.obtenerProductoPorId(id);

        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/productos/";
        }

        List<Categoria> categorias = categoriaService.listarCategoriasOrdenadas();

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);
        model.addAttribute("titulo", "Editar Producto");
        return "productos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (!productoService.existeProductoPorId(id)) {
                redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
                return "redirect:/productos/";
            }

            productoService.eliminarProducto(id);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el producto: " + e.getMessage());
        }

        return "redirect:/productos/";
    }

    @GetMapping("/buscar")
    public String buscarProductos(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String laboratorio,
            Model model) {

        List<Producto> productos;
        List<Categoria> categorias = categoriaService.listarCategoriasOrdenadas();

        if ((nombre != null && !nombre.trim().isEmpty())
                || categoriaId != null
                || (laboratorio != null && !laboratorio.trim().isEmpty())) {

            productos = productoService.buscarProductosPorCriterios(nombre, categoriaId, laboratorio);
            model.addAttribute("busquedaNombre", nombre);
            model.addAttribute("busquedaCategoriaId", categoriaId);
            model.addAttribute("busquedaLaboratorio", laboratorio);
        } else {
            productos = productoService.listarProductosOrdenados();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("titulo", "Buscar Productos");
        return "productos/lista";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleProducto(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Producto producto = productoService.obtenerProductoPorId(id);

        if (producto == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/productos/";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Detalle del Producto");
        return "productos/detalle";
    }

    @GetMapping("/stock-bajo")
    public String productosStockBajo(Model model) {
        List<Producto> productos = productoService.obtenerProductosConStockBajo();
        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Productos con Stock Bajo");
        return "productos/stock-bajo";
    }
}
