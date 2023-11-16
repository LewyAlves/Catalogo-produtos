package com.catalogo.api.controller;


import com.catalogo.api.domain.catalogo.Catalogo;
import com.catalogo.api.domain.catalogo.DadosProdutos;
import com.catalogo.api.domain.catalogo.CatalogoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/catalogo")
public class ProdutosController {

    @Autowired
    CatalogoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraProduto(@RequestBody @Valid DadosProdutos dados){
        var produtos = (new Catalogo(dados));
        var repositorio = repository.save(produtos);
        return ResponseEntity.ok(produtos);
    }
}
