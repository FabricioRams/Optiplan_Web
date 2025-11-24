package com.optiplan.web.repository;

import com.optiplan.web.model.Tarea;
import com.optiplan.web.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    List<Tarea> findByUsuarioAndEstado(Usuario usuario, String estado);
    
    List<Tarea> findByUsuarioAndFechaBetween(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin);
    
    @Query("SELECT t FROM Tarea t WHERE t.usuario = :usuario AND t.fecha BETWEEN :fechaInicio AND :fechaFin AND t.estado = 'Pendiente'")
    List<Tarea> findTareasPendientesPorRangoFecha(
        @Param("usuario") Usuario usuario, 
        @Param("fechaInicio") LocalDate fechaInicio, 
        @Param("fechaFin") LocalDate fechaFin
    );
    
    @Query("SELECT t FROM Tarea t WHERE t.recordatorioActivo = true AND t.estado = 'Pendiente'")
    List<Tarea> findTareasConRecordatoriosActivos();
    
    List<Tarea> findByUsuarioOrderByFechaAsc(Usuario usuario);
}
