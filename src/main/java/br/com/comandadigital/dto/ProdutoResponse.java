package br.com.comandadigital.dto;

import br.com.comandadigital.domain.Produto;
import br.com.comandadigital.domain.TagProduto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public record ProdutoResponse(
        Long id,
        String nome,
        BigDecimal preco,
        boolean disponivel,
        Set<String> tags
) {
    public static ProdutoResponse from(Produto produto) {
        Set<String> nomesTags = produto.getTags().stream()
                .map(TagProduto::getNome)
                .collect(Collectors.toCollection(TreeSet::new));
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.isDisponivel(),
                nomesTags
        );
    }
}
