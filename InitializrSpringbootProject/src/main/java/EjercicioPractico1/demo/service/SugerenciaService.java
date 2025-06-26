/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.service;

import EjercicioPractico1.demo.domain.Sugerencia;
import java.time.LocalDateTime;
import java.util.List;

public interface SugerenciaService {
    
    // Operaciones CRUD básicas
    List<Sugerencia> listarSugerencias();
    Sugerencia obtenerSugerenciaPorId(Long id);
    Sugerencia guardarSugerencia(Sugerencia sugerencia);
    void eliminarSugerencia(Long id);
    
    // Operaciones de búsqueda
    List<Sugerencia> buscarSugerenciasPorTipo(String tipo);
    List<Sugerencia> buscarSugerenciasPorEmail(String email);
    List<Sugerencia> buscarSugerenciasPorRangoFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Operaciones especiales
    List<Sugerencia> listarSugerenciasOrdenadasPorFecha();
    List<Sugerencia> obtenerSugerenciasRecientes();
    Long contarSugerenciasPorTipo(String tipo);
    
    // Operaciones de validación
    boolean existeSugerenciaPorId(Long id);
    boolean validarTipoSugerencia(String tipo);
    
    // Operaciones de estadísticas
    Long contarQuejas();
    Long contarSugerencias();
    List<Sugerencia> obtenerUltimasSugerencias(int limite);
}