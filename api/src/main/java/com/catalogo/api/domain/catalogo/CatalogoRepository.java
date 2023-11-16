package com.catalogo.api.domain.catalogo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
    List<Catalogo> findByProdutoContaining(String produto);

    List<Catalogo> findBySecao(secao secao);
}
