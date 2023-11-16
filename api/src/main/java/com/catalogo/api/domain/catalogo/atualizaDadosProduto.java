package com.catalogo.api.domain.catalogo;

import jakarta.validation.constraints.NotNull;

public record atualizaDadosProduto(@NotNull Long id, String produto, String descricao, Double preco) {
}
