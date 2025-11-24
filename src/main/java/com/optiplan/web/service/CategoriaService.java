package com.optiplan.web.service;

import com.optiplan.web.model.Categoria;
import com.optiplan.web.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }
    
    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }
}
