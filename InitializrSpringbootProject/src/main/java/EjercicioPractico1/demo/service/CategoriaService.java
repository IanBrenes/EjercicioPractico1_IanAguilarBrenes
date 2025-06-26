/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.service;

import EjercicioPractico1.demo.domain.Categoria;
import java.util.List;

public interface CategoriaService {
    
    // Operaciones CRUD básicas
    List<Categoria> listarCategorias();
    Categoria obtenerCategoriaPorId(Long id);
    Categoria guardarCategoria(Categoria categoria);
    void eliminarCategoria(Long id);
    
    // Operaciones de búsqueda
    List<Categoria> buscarCategoriasPorNombre(String nombre);
    Categoria buscarCategoriaPorNombreExacto(String nombre);
    
    // Operaciones de validación
    boolean existeCategoriaPorNombre(String nombre);
    boolean existeCategoriaPorId(Long id);
    
    // Operaciones especiales
    List<Categoria> listarCategoriasOrdenadas();
}
