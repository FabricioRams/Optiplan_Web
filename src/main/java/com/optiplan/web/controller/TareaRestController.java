package com.optiplan.web.controller;

import com.optiplan.web.model.Tarea;
import com.optiplan.web.model.Usuario;
import com.optiplan.web.model.Categoria;
import com.optiplan.web.service.TareaService;
import com.optiplan.web.service.CategoriaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaRestController {
    
    private final TareaService tareaService;
    private final CategoriaService categoriaService;
    
    @PostMapping
    public ResponseEntity<?> crearTarea(
            @RequestBody Map<String, Object> tareaData,
            HttpSession session) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return ResponseEntity.status(401).body(Map.of("error", "No autenticado"));
        }
        
        try {
            Tarea tarea = new Tarea();
            tarea.setUsuario(usuario);
            tarea.setTitulo((String) tareaData.get("titulo"));
            tarea.setDescripcion((String) tareaData.get("descripcion"));
            tarea.setPrioridad((String) tareaData.get("prioridad"));
            tarea.setEstado("Pendiente");
            tarea.setFecha(LocalDate.parse((String) tareaData.get("fecha")));
            
            if (tareaData.get("horaFin") != null) {
                tarea.setHoraFin(LocalTime.parse((String) tareaData.get("horaFin")));
            }
            
            Long categoriaId = Long.parseLong(tareaData.get("categoriaId").toString());
            Categoria categoria = categoriaService.buscarPorId(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            tarea.setCategoria(categoria);
            
            Tarea nuevaTarea = tareaService.crearTarea(tarea);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Tarea creada exitosamente",
                "tarea", nuevaTarea
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Error al crear tarea: " + e.getMessage()
            ));
        }
    }
    
    @PutMapping("/{id}/completar")
    public ResponseEntity<?> completarTarea(@PathVariable Long id) {
        boolean resultado = tareaService.cambiarEstadoTarea(id, "Completado");
        
        if (resultado) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Tarea completada"
            ));
        }
        
        return ResponseEntity.badRequest().body(Map.of(
            "success", false,
            "message", "Error al completar tarea"
        ));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarea(@PathVariable Long id) {
        boolean resultado = tareaService.eliminarTarea(id);
        
        if (resultado) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Tarea eliminada"
            ));
        }
        
        return ResponseEntity.badRequest().body(Map.of(
            "success", false,
            "message", "Error al eliminar tarea"
        ));
    }
    
    @PutMapping("/{id}/titulo")
    public ResponseEntity<?> actualizarTitulo(
            @PathVariable Long id,
            @RequestBody Map<String, String> data) {
        
        String nuevoTitulo = data.get("titulo");
        boolean resultado = tareaService.actualizarTitulo(id, nuevoTitulo);
        
        if (resultado) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Título actualizado"
            ));
        }
        
        return ResponseEntity.badRequest().body(Map.of(
            "success", false,
            "message", "Error al actualizar título"
        ));
    }
    
    @PostMapping("/{id}/recordatorio")
    public ResponseEntity<?> activarRecordatorio(
            @PathVariable Long id,
            @RequestBody Map<String, String> data) {
        
        try {
            LocalTime horaRecordatorio = LocalTime.parse(data.get("hora"));
            boolean resultado = tareaService.activarRecordatorio(id, horaRecordatorio);
            
            if (resultado) {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Recordatorio activado"
                ));
            }
            
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Error al activar recordatorio"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Formato de hora inválido"
            ));
        }
    }
    
    @DeleteMapping("/{id}/recordatorio")
    public ResponseEntity<?> desactivarRecordatorio(@PathVariable Long id) {
        boolean resultado = tareaService.desactivarRecordatorio(id);

        if (resultado) {
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Recordatorio desactivado"
            ));
        }

        return ResponseEntity.badRequest().body(Map.of(
            "success", false,
            "message", "Error al desactivar recordatorio"
        ));
    }

    @GetMapping("/recordatorios-activos")
    public ResponseEntity<?> obtenerRecordatoriosActivos(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return ResponseEntity.status(401).body(Map.of("error", "No autenticado"));
        }

        try {
            var tareas = tareaService.obtenerTareasConRecordatoriosActivos();
            // Filtrar solo las tareas del usuario actual
            var tareasFiltradas = tareas.stream()
                .filter(t -> t.getUsuario().getId().equals(usuario.getId()))
                .toList();

            return ResponseEntity.ok(Map.of(
                "success", true,
                "tareas", tareasFiltradas
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Error al obtener recordatorios"
            ));
        }
    }
}
