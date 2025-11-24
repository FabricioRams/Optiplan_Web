package com.optiplan.web.service;

import com.optiplan.web.model.Tarea;
import com.optiplan.web.model.Usuario;
import com.optiplan.web.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TareaService {
    
    private final TareaRepository tareaRepository;
    
    @Transactional
    public Tarea crearTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }
    
    public List<Tarea> obtenerTareasPorEstado(Usuario usuario, String estado) {
        return tareaRepository.findByUsuarioAndEstado(usuario, estado);
    }
    
    public List<Tarea> obtenerTareasPorRangoFecha(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        return tareaRepository.findTareasPendientesPorRangoFecha(usuario, fechaInicio, fechaFin);
    }
    
    public List<Tarea> obtenerTodasLasTareas(Usuario usuario) {
        return tareaRepository.findByUsuarioOrderByFechaAsc(usuario);
    }
    
    @Transactional
    public boolean cambiarEstadoTarea(Long tareaId, String nuevoEstado) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setEstado(nuevoEstado);
            tareaRepository.save(tarea);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean eliminarTarea(Long tareaId) {
        if (tareaRepository.existsById(tareaId)) {
            tareaRepository.deleteById(tareaId);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean actualizarTitulo(Long tareaId, String nuevoTitulo) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setTitulo(nuevoTitulo);
            tareaRepository.save(tarea);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean activarRecordatorio(Long tareaId, LocalTime horaRecordatorio) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setRecordatorioActivo(true);
            tarea.setHoraRecordatorio(horaRecordatorio);
            tareaRepository.save(tarea);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean desactivarRecordatorio(Long tareaId) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setRecordatorioActivo(false);
            tarea.setHoraRecordatorio(null);
            tareaRepository.save(tarea);
            return true;
        }
        return false;
    }
    
    public List<Tarea> obtenerTareasConRecordatoriosActivos() {
        return tareaRepository.findTareasConRecordatoriosActivos();
    }
    
    public Optional<Tarea> buscarPorId(Long id) {
        return tareaRepository.findById(id);
    }
}
