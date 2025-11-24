package com.optiplan.web.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        // Permitir acceso a páginas públicas
        if (requestURI.equals("/") ||
            requestURI.equals("/login") ||
            requestURI.equals("/registro") ||
            requestURI.startsWith("/css/") ||
            requestURI.startsWith("/js/") ||
            requestURI.startsWith("/images/")) {
            return true;
        }

        // Verificar si el usuario está logueado
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
