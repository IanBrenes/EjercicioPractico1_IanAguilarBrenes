/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("titulo", "Farmacia San José");
        model.addAttribute("mensaje", "Bienvenido a nuestra farmacia");
        return "index";
    }
    
    @GetMapping("/nosotros")
    public String nosotros(Model model) {
        model.addAttribute("titulo", "Acerca de Nosotros");
        return "nosotros";
    }
    
    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("titulo", "Contacto");
        return "contacto";
    }
}
