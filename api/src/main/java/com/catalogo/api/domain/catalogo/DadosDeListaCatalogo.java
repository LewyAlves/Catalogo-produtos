package com.catalogo.api.domain.catalogo;

public record DadosDeListaCatalogo(Long id, String produto, secao secao, String descricao, double preco) {
    public DadosDeListaCatalogo(Catalogo catalogo){
        this(catalogo.getId(), catalogo.getProduto(), catalogo.getSecao(), catalogo.getDescricao(), catalogo.getPreco());
    }
}
