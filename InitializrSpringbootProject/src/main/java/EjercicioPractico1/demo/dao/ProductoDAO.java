/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.dao;

import EjercicioPractico1.demo.domain.Producto;
import EjercicioPractico1.demo.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductoDAO extends JpaRepository<Producto, Long> {
    
    // Buscar productos por nombre
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar productos por categoría
    List<Producto> findByCategoria(Categoria categoria);
    
    // Buscar productos por categoría ID
    List<Producto> findByCategoriaIdCategoria(Long idCategoria);
    
    // Buscar productos por laboratorio
    List<Producto> findByLaboratorioContainingIgnoreCase(String laboratorio);
    
    // Buscar productos por rango de precio
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    
    // Buscar productos con stock disponible
    List<Producto> findByStockGreaterThan(Integer stock);
    
    // Buscar productos que requieren receta
    List<Producto> findByRequiereReceta(Boolean requiereReceta);
    
    // Obtener productos ordenados por nombre
    @Query("SELECT p FROM Producto p ORDER BY p.nombre ASC")
    List<Producto> findAllOrderByNombre();
    
    // Obtener productos con stock bajo (menor a 10)
    @Query("SELECT p FROM Producto p WHERE p.stock < 10")
    List<Producto> findProductosConStockBajo();
    
    // Buscar productos por múltiples criterios
    @Query("SELECT p FROM Producto p WHERE " +
           "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:idCategoria IS NULL OR p.categoria.idCategoria = :idCategoria) AND " +
           "(:laboratorio IS NULL OR LOWER(p.laboratorio) LIKE LOWER(CONCAT('%', :laboratorio, '%')))")
    List<Producto> findByMultiplesCriterios(@Param("nombre") String nombre, 
                                          @Param("idCategoria") Long idCategoria, 
                                          @Param("laboratorio") String laboratorio);
}
