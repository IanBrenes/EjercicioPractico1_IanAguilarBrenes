/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.service;

import EjercicioPractico1.demo.domain.Producto;
import EjercicioPractico1.demo.domain.Categoria;
import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {
    
    // Operaciones CRUD básicas
    List<Producto> listarProductos();
    Producto obtenerProductoPorId(Long id);
    Producto guardarProducto(Producto producto);
    void eliminarProducto(Long id);
    
    // Operaciones de búsqueda
    List<Producto> buscarProductosPorNombre(String nombre);
    List<Producto> buscarProductosPorCategoria(Categoria categoria);
    List<Producto> buscarProductosPorCategoriaId(Long idCategoria);
    List<Producto> buscarProductosPorLaboratorio(String laboratorio);
    List<Producto> buscarProductosPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax);
    List<Producto> buscarProductosConStock(Integer stockMinimo);
    List<Producto> buscarProductosConReceta(Boolean requiereReceta);
    
    // Operaciones especiales
    List<Producto> listarProductosOrdenados();
    List<Producto> obtenerProductosConStockBajo();
    List<Producto> buscarProductosPorCriterios(String nombre, Long idCategoria, String laboratorio);
    
    // Operaciones de validación
    boolean existeProductoPorId(Long id);
    boolean validarStockDisponible(Long idProducto, Integer cantidadSolicitada);
    
    // Operaciones de inventario
    void actualizarStock(Long idProducto, Integer nuevoStock);
    void reducirStock(Long idProducto, Integer cantidad);
}