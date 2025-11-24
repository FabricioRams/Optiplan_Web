package com.optiplan.web.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoriaID")
    private Long id;
    
    @Column(name = "Nombre", length = 50)
    private String nombre;
    
    @OneToMany(mappedBy = "categoria")
    private List<Tarea> tareas = new ArrayList<>();
    
    public Categoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
