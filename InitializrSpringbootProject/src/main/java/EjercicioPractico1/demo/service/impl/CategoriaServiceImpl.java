/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.service.impl;

import EjercicioPractico1.demo.dao.CategoriaDAO;
import EjercicioPractico1.demo.domain.Categoria;
import EjercicioPractico1.demo.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    
    @Autowired
    private CategoriaDAO categoriaDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listarCategorias() {
        return categoriaDAO.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaDAO.findById(id).orElse(null);
    }
    
    @Override
    @Transactional
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaDAO.save(categoria);
    }
    
    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        categoriaDAO.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> buscarCategoriasPorNombre(String nombre) {
        return categoriaDAO.findByNombreContainingIgnoreCase(nombre);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Categoria buscarCategoriaPorNombreExacto(String nombre) {
        return categoriaDAO.findByNombre(nombre);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeCategoriaPorNombre(String nombre) {
        return categoriaDAO.existsByNombre(nombre);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeCategoriaPorId(Long id) {
        return categoriaDAO.existsById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listarCategoriasOrdenadas() {
        return categoriaDAO.findAllOrderByNombre();
    }
}
