package com.catalogo.api.domain.catalogo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Produtos")
@Entity(name = "Produto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Catalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeProduto;
    private secao secao;
    private String descricao;
    private Double preco;

    public Catalogo(DadosProdutos dados){
        this.nomeProduto = dados.nomeProduto();
        this.secao = dados.secao();
        this.descricao = dados.descricao();
        this.preco = dados.preco();
    }
}
