/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package EjercicioPractico1.demo.dao;

import EjercicioPractico1.demo.domain.Sugerencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SugerenciaDAO extends JpaRepository<Sugerencia, Long> {
    
    // Buscar sugerencias por tipo (QUEJA o SUGERENCIA)
    List<Sugerencia> findByTipo(String tipo);
    
    // Buscar sugerencias por email
    List<Sugerencia> findByEmail(String email);
    
    // Buscar sugerencias por rango de fechas
    List<Sugerencia> findByFechaCreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Obtener todas las sugerencias ordenadas por fecha de creación descendente
    @Query("SELECT s FROM Sugerencia s ORDER BY s.fechaCreacion DESC")
    List<Sugerencia> findAllOrderByFechaCreacionDesc();
    
    // Contar sugerencias por tipo
    @Query("SELECT COUNT(s) FROM Sugerencia s WHERE s.tipo = :tipo")
    Long countByTipo(String tipo);
    
    // Obtener sugerencias recientes (últimos 7 días)
    @Query("SELECT s FROM Sugerencia s WHERE s.fechaCreacion >= :fechaLimite ORDER BY s.fechaCreacion DESC")
    List<Sugerencia> findSugerenciasRecientes(LocalDateTime fechaLimite);
}