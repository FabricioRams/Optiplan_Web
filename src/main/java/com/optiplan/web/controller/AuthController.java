package com.optiplan.web.controller;

import com.optiplan.web.model.Usuario;
import com.optiplan.web.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @GetMapping("/")
    public String index(HttpSession session) {
        // Si ya está logueado, redirigir al dashboard
        if (session.getAttribute("usuario") != null) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            HttpSession session,
            Model model) {

        // Si ya está logueado, redirigir al dashboard
        if (session.getAttribute("usuario") != null) {
            return "redirect:/dashboard";
        }

        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("message", "Sesión cerrada correctamente");
        }

        return "login";
    }

    @GetMapping("/registro")
    public String registroPage() {
        return "registro";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String correo,
            @RequestParam String contrasena,
            HttpSession session,
            Model model) {

        try {
            if (usuarioService.verificarCredenciales(correo, contrasena)) {
                Optional<Usuario> usuario = usuarioService.buscarPorCorreo(correo);
                if (usuario.isPresent()) {
                    session.setAttribute("usuario", usuario.get());
                    session.setAttribute("usuarioId", usuario.get().getId());
                    session.setAttribute("usuarioNombre", usuario.get().getNombre());
                    return "redirect:/dashboard";
                }
            }

            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Error al iniciar sesión: " + e.getMessage());
            return "login";
        }
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String contrasena,
            Model model) {

        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setCorreo(correo);
            nuevoUsuario.setContrasena(contrasena);

            usuarioService.registrarUsuario(nuevoUsuario);
            model.addAttribute("message", "Usuario registrado correctamente. Inicia sesión.");
            return "redirect:/login?message=registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}
