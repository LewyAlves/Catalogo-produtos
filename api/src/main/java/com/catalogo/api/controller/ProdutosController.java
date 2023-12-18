package com.catalogo.api.controller;


import com.catalogo.api.domain.catalogo.*;
import com.catalogo.api.service.ProdutosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogo")
@SecurityRequirement(name = "bearer-key")
public class ProdutosController {

    @Autowired
    CatalogoRepository repository;

    @Autowired
    ProdutosService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastraProduto(@RequestBody @Valid DadosProdutos dados){
        var produtos = (new Catalogo(dados));
        var produtoSalvo = repository.save(produtos);
        return ResponseEntity.ok(produtoSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<DadosDeListaCatalogo>> lista(@PageableDefault(sort = {"secao"}, size = Integer.MAX_VALUE) Pageable paginacao){
        var page = repository.findAll(paginacao).map(DadosDeListaCatalogo::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizaProduto (@RequestBody @Valid atualizaDadosProduto dados){
        var produto = repository.getReferenceById(dados.id());
        produto.atualizaDados(dados);

        return ResponseEntity.ok(new  detalhaAtualizacaoDoProduto(produto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelaProduto(@PathVariable Long id){
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DadosProdutos>> buscarPorIdProdutoOuSecao(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String produto,
            @RequestParam(required = false) secao secao) {
        var pesquisaProduto = service.PesquisaProduto(id, produto, secao);
        return ResponseEntity.ok(pesquisaProduto);

    }
}
