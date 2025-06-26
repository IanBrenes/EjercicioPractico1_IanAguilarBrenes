/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.service.impl;

import EjercicioPractico1.demo.dao.ProductoDAO;
import EjercicioPractico1.demo.domain.Producto;
import EjercicioPractico1.demo.domain.Categoria;
import EjercicioPractico1.demo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired
    private ProductoDAO productoDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoDAO.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Producto obtenerProductoPorId(Long id) {
        return productoDAO.findById(id).orElse(null);
    }
    
    @Override
    @Transactional
    public Producto guardarProducto(Producto producto) {
        return productoDAO.save(producto);
    }
    
    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        productoDAO.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoDAO.findByNombreContainingIgnoreCase(nombre);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosPorCategoria(Categoria categoria) {
        return productoDAO.findByCategoria(categoria);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosPorCategoriaId(Long idCategoria) {
        return productoDAO.findByCategoriaIdCategoria(idCategoria);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosPorLaboratorio(String laboratorio) {
        return productoDAO.findByLaboratorioContainingIgnoreCase(laboratorio);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax) {
        return productoDAO.findByPrecioBetween(precioMin, precioMax);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosConStock(Integer stockMinimo) {
        return productoDAO.findByStockGreaterThan(stockMinimo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosConReceta(Boolean requiereReceta) {
        return productoDAO.findByRequiereReceta(requiereReceta);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductosOrdenados() {
        return productoDAO.findAllOrderByNombre();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerProductosConStockBajo() {
        return productoDAO.findProductosConStockBajo();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarProductosPorCriterios(String nombre, Long idCategoria, String laboratorio) {
        return productoDAO.findByMultiplesCriterios(nombre, idCategoria, laboratorio);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeProductoPorId(Long id) {
        return productoDAO.existsById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean validarStockDisponible(Long idProducto, Integer cantidadSolicitada) {
        Producto producto = obtenerProductoPorId(idProducto);
        return producto != null && producto.getStock() >= cantidadSolicitada;
    }
    
    @Override
    @Transactional
    public void actualizarStock(Long idProducto, Integer nuevoStock) {
        Producto producto = obtenerProductoPorId(idProducto);
        if (producto != null) {
            producto.setStock(nuevoStock);
            productoDAO.save(producto);
        }
    }
    
    @Override
    @Transactional
    public void reducirStock(Long idProducto, Integer cantidad) {
        Producto producto = obtenerProductoPorId(idProducto);
        if (producto != null && producto.getStock() >= cantidad) {
            producto.setStock(producto.getStock() - cantidad);
            productoDAO.save(producto);
        }
    }
}