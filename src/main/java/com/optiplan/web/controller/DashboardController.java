package com.optiplan.web.controller;

import com.optiplan.web.model.Usuario;
import com.optiplan.web.model.Tarea;
import com.optiplan.web.service.TareaService;
import com.optiplan.web.service.CategoriaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final TareaService tareaService;
    private final CategoriaService categoriaService;
    
    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("categorias", categoriaService.obtenerTodasLasCategorias());
        
        return "dashboard";
    }
    
    @GetMapping("/calendario")
    public String calendario(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // Obtener tareas del mes actual
        LocalDate hoy = LocalDate.now();
        LocalDate inicioMes = hoy.withDayOfMonth(1);
        LocalDate finMes = hoy.with(TemporalAdjusters.lastDayOfMonth());
        
        List<Tarea> tareas = tareaService.obtenerTareasPorRangoFecha(usuario, inicioMes, finMes);
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("tareas", tareas);
        model.addAttribute("mesActual", hoy);
        
        return "fragments/calendario :: calendario";
    }
    
    @GetMapping("/semana")
    public String semana(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // Obtener tareas de la semana actual
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.with(DayOfWeek.MONDAY);
        LocalDate finSemana = hoy.with(DayOfWeek.SUNDAY);
        
        List<Tarea> tareas = tareaService.obtenerTareasPorRangoFecha(usuario, inicioSemana, finSemana);
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("tareas", tareas);
        model.addAttribute("semanaActual", hoy);
        
        return "fragments/semana :: semana";
    }
    
    @GetMapping("/tareas")
    public String tareas(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        List<Tarea> tareasPendientes = tareaService.obtenerTareasPorEstado(usuario, "Pendiente");
        List<Tarea> tareasCompletadas = tareaService.obtenerTareasPorEstado(usuario, "Completado");
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("tareasPendientes", tareasPendientes);
        model.addAttribute("tareasCompletadas", tareasCompletadas);
        model.addAttribute("categorias", categoriaService.obtenerTodasLasCategorias());
        
        return "fragments/tareas :: tareas";
    }
    
    @GetMapping("/recordatorios")
    public String recordatorios(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        List<Tarea> tareasPendientes = tareaService.obtenerTareasPorEstado(usuario, "Pendiente");
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("tareas", tareasPendientes);
        
        return "fragments/recordatorios :: recordatorios";
    }
}
