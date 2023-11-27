package com.catalogo.api.infra.security;

import com.catalogo.api.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recuperatoken(request);

        if (token != null) {
            var subject = tokenService.getSubject(token);

            if (subject != null) {
                var usuario = usuarioRepository.findByLogin(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                handleAccessDenied(response);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void handleAccessDenied(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{ \"Erro\": \"Desculpe, parece que você não tem permissão para acessar esta requisição. Se você é um usuário registrado, pode ser que o seu token de acesso tenha expirado." +
                " Por favor, faça login novamente para resolver esse problema. Se você continuar enfrentando dificuldades, entre em contato com o suporte. Obrigado pela compreensão.\" }");
        response.getWriter().flush();
    }

    private String recuperatoken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }

        return null;
    }
}
