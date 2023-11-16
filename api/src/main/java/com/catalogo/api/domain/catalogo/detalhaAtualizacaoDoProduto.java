package com.catalogo.api.domain.catalogo;

public record detalhaAtualizacaoDoProduto(Long id, String produto, String descricao, double preco) {
    public detalhaAtualizacaoDoProduto(Catalogo catalogo){
        this(catalogo.getId(), catalogo.getProduto(), catalogo.getDescricao(), catalogo.getPreco());
    }
}
