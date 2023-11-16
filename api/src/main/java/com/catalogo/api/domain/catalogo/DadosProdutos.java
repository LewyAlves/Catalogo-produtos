package com.catalogo.api.domain.catalogo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosProdutos(
        @NotBlank
        String nomeProduto,
        @NotNull
        secao secao,
        @NotNull
        String descricao,
        @NotNull
        @DecimalMin(value = "0.1")
        Double preco
) {
}
