package com.catalogo.api.controller;


import com.catalogo.api.domain.catalogo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogo")
@SecurityRequirement(name = "bearer-key")
public class ProdutosController {

    @Autowired
    CatalogoRepository repository;

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

        if (id != null) {
            Optional<Catalogo> produtoPorId = repository.findById(id);
            return produtoPorId.map(p -> ResponseEntity.ok(List.of(mapToDto(p))))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else if (produto != null) {
            List<Catalogo> produtosPorProduto = repository.findByProdutoContaining(produto);
            return ResponseEntity.ok(mapListToDto(produtosPorProduto));
        } else if (secao != null) {
            List<Catalogo> produtosPorSecao = repository.findBySecao(secao);
            return ResponseEntity.ok(mapListToDto(produtosPorSecao));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private List<DadosProdutos> mapListToDto(List<Catalogo> catalogos) {
        return catalogos.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private DadosProdutos mapToDto(Catalogo catalogo) {
        return new DadosProdutos(
                catalogo.getProduto(),
                catalogo.getSecao(),
                catalogo.getDescricao(),
                catalogo.getPreco()
        );
    }


}
