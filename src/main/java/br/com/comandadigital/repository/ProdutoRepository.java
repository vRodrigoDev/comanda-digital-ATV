package br.com.comandadigital.repository;

import br.com.comandadigital.domain.Produto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @EntityGraph(attributePaths = "tags")
    List<Produto> findByDisponivelTrueOrderByNomeAsc();

    List<Produto> findByDisponivelTrue();
}
