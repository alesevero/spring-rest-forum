package com.alesevero.forum.controller;

import com.alesevero.forum.config.security.TokenService;
import com.alesevero.forum.controller.dto.TokenDTO;
import com.alesevero.forum.controller.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    public AutenticacaoController
            (AuthenticationManager authenticationManager,
             TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity autenticar(@RequestBody @Valid LoginForm form) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(form.converter());
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch (AuthenticationException ae) {
            return ResponseEntity.badRequest().build();
        }
    }
}
