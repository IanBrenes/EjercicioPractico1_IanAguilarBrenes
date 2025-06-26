/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.service.impl;

import EjercicioPractico1.demo.dao.SugerenciaDAO;
import EjercicioPractico1.demo.domain.Sugerencia;
import EjercicioPractico1.demo.service.SugerenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SugerenciaServiceImpl implements SugerenciaService {
    
    @Autowired
    private SugerenciaDAO sugerenciaDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<Sugerencia> listarSugerencias() {
        return sugerenciaDAO.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Sugerencia obtenerSugerenciaPorId(Long id) {
        return sugerenciaDAO.findById(id).orElse(null);
    }
    
    @Override
    @Transactional
    public Sugerencia guardarSugerencia(Sugerencia sugerencia) {
        return sugerenciaDAO.save(sugerencia);
    }
    
    @Override
    @Transactional
    public void eliminarSugerencia(Long id) {
        sugerenciaDAO.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sugerencia> buscarSugerenciasPorTipo(String tipo) {
        return sugerenciaDAO.findByTipo(tipo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sugerencia> buscarSugerenciasPorEmail(String email) {
        return sugerenciaDAO.findByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sugerencia> buscarSugerenciasPorRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return sugerenciaDAO.findByFechaCreacionBetween(fechaInicio, fechaFin);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sugerencia> listarSugerenciasOrdenadasPorFecha() {
        return sugerenciaDAO.findAllOrderByFechaCreacionDesc();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sugerencia> obtenerSugerenciasRecientes() {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(7);
        return sugerenciaDAO.findSugerenciasRecientes(fechaLimite);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long contarSugerenciasPorTipo(String tipo) {
        return sugerenciaDAO.countByTipo(tipo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeSugerenciaPorId(Long id) {
        return sugerenciaDAO.existsById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean validarTipoSugerencia(String tipo) {
        return "QUEJA".equalsIgnoreCase(tipo) || "SUGERENCIA".equalsIgnoreCase(tipo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long contarQuejas() {
        return sugerenciaDAO.countByTipo("QUEJA");
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long contarSugerencias() {
        return sugerenciaDAO.countByTipo("SUGERENCIA");
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sugerencia> obtenerUltimasSugerencias(int limite) {
        List<Sugerencia> todasSugerencias = listarSugerenciasOrdenadasPorFecha();
        return todasSugerencias.stream()
                .limit(limite)
                .toList();
    }
}
