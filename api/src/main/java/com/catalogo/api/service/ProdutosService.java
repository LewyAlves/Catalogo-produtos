package com.catalogo.api.service;

import com.catalogo.api.domain.catalogo.Catalogo;
import com.catalogo.api.domain.catalogo.CatalogoRepository;
import com.catalogo.api.domain.catalogo.DadosProdutos;
import com.catalogo.api.domain.catalogo.secao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutosService {

    @Autowired
    CatalogoRepository repository;

    public List<DadosProdutos> PesquisaProduto(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String produto,
            @RequestParam(required = false) secao secao){
        if (id != null) {
            Optional<Catalogo> produtoPorId = repository.findById(id);
            return mapToDtoListOptional(produtoPorId);
        } else if (produto != null) {
            List<Catalogo> produtosPorProduto = repository.findByProdutoContaining(produto);
            return mapListToDto(produtosPorProduto);
        } else if (secao != null) {
            List<Catalogo> produtosPorSecao = repository.findBySecao(secao);
            return mapListToDto(produtosPorSecao);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }
    private List<DadosProdutos> mapListToDto(List<Catalogo> catalogos) {
        return catalogos.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private List<DadosProdutos> mapToDtoListOptional(Optional<Catalogo> catalogoOptional) {
        return catalogoOptional
                .map(Collections::singletonList)
                .orElse(Collections.emptyList())
                .stream()
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
