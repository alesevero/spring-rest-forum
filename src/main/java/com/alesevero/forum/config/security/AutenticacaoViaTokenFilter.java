package com.alesevero.forum.config.security;

import com.alesevero.forum.model.Usuario;
import com.alesevero.forum.repository.Usuarios;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private final Usuarios usuarios;
    private TokenService tokenService;

    AutenticacaoViaTokenFilter
            (TokenService tokenService,
             Usuarios usuarios) {
        this.tokenService = tokenService;
        this.usuarios = usuarios;
    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest httpServletRequest,
             HttpServletResponse httpServletResponse,
             FilterChain filterChain)
            throws ServletException, IOException {
        String token = recuperarToken(httpServletRequest);
        boolean isValido = tokenService.isTokenValido(token);
        if (isValido) {
            validarCliente(token);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validarCliente(String token) {
        usuarios.findById(tokenService.getIdUsuario(token))
                .map(this::autenticar)
                .orElseThrow(RuntimeException::new);
    }

    private Usuario autenticar(Usuario usuario) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken
                        (usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return usuario;
    }



    private String recuperarToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("authorization");
        if (token == null || token.isEmpty() || !token
                .startsWith("Bearer")) {
            return null;
        }
        return token.substring(7);
    }
}
