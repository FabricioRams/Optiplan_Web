package com.optiplan.web.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TareaID")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UsuarioID")
    private Usuario usuario;
    
    @Column(name = "Titulo", length = 100)
    private String titulo;
    
    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoriaID")
    private Categoria categoria;
    
    @Column(name = "Prioridad", length = 20)
    private String prioridad;
    
    @Column(name = "Estado", length = 20)
    private String estado;
    
    @Column(name = "Fecha")
    private LocalDate fecha;
    
    @Column(name = "HoraInicio")
    private LocalTime horaInicio;
    
    @Column(name = "HoraFin")
    private LocalTime horaFin;
    
    @Column(name = "FechaCreacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "RecordatorioActivo")
    private Boolean recordatorioActivo = false;
    
    @Column(name = "HoraRecordatorio")
    private LocalTime horaRecordatorio;
    
    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "Pendiente";
        }
        if (recordatorioActivo == null) {
            recordatorioActivo = false;
        }
    }
}
