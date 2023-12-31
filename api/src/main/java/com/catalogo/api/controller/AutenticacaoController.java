package com.catalogo.api.controller;

import com.catalogo.api.domain.usuario.DadosAutorizacao;
import com.catalogo.api.domain.usuario.Usuario;
import com.catalogo.api.infra.security.TokenJWT;
import com.catalogo.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autorizacao")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosAutorizacao dados){
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(token);
        var usuarioAutenticado = (Usuario) authentication.getPrincipal();
        var tokenGerado = tokenService.geraToken(usuarioAutenticado);
        return ResponseEntity.ok(new TokenJWT(tokenGerado));
    }
}
