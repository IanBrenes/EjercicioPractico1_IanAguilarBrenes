/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.dao;

import EjercicioPractico1.demo.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Long> {
    
    // Buscar categorías por nombre
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar categoría por nombre exacto
    Categoria findByNombre(String nombre);
    
    // Obtener todas las categorías ordenadas por nombre
    @Query("SELECT c FROM Categoria c ORDER BY c.nombre ASC")
    List<Categoria> findAllOrderByNombre();
    
    // Verificar si existe una categoría con el nombre dado
    boolean existsByNombre(String nombre);
}