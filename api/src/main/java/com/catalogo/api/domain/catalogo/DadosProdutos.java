package com.catalogo.api.domain.catalogo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosProdutos(
        @NotNull
        String produto,
        @NotNull
        secao secao,
        @NotNull
        String descricao,
        @NotNull
        @DecimalMin(value = "0.1")
        Double preco
) {
}
