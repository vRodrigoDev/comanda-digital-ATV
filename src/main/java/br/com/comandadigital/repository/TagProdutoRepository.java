package br.com.comandadigital.repository;

import br.com.comandadigital.domain.TagProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagProdutoRepository extends JpaRepository<TagProduto, Long> {

    TagProduto findByNome(String nome);

    Optional<TagProduto> findByNomeIgnoreCase(String nome);
}
