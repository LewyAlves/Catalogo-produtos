package com.catalogo.api.domain.catalogo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "Catalogo")
@Entity(name = "Catalogo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Catalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String produto;
    @Enumerated(EnumType.STRING)
    private secao secao;
    private String descricao;
    private Double preco;

    public Catalogo(DadosProdutos dados){
        this.produto = dados.produto();
        this.secao = dados.secao();
        this.descricao = dados.descricao();
        this.preco = dados.preco();
    }
}
