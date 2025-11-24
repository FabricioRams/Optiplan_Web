package com.optiplan.web.service;

import com.optiplan.web.model.Usuario;
import com.optiplan.web.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Encriptar contraseña con BCrypt
        String contrasenaEncriptada = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());
        usuario.setContrasena(contrasenaEncriptada);
        usuario.setFechaRegistro(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public boolean verificarCredenciales(String correo, String contrasena) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);
        if (usuario.isPresent()) {
            // Verificar contraseña con BCrypt
            return BCrypt.checkpw(contrasena, usuario.get().getContrasena());
        }
        return false;
    }
}
