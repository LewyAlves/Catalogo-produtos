package com.catalogo.api.domain.catalogo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosProdutos(
        @NotNull(message = "O nome do produto precisa ser informado")
        String produto,
        @NotNull(message = "o campo seção é obrigatorio")
        secao secao,
        @NotNull(message = "descrição do produto obrigatorio")
        String descricao,
        @NotNull
        @DecimalMin(value = "0.1")
        Double preco
) {
}
