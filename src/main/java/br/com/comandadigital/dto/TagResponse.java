package br.com.comandadigital.dto;

import br.com.comandadigital.domain.TagProduto;

public record TagResponse(Long id, String nome) {

    public static TagResponse from(TagProduto tag) {
        return new TagResponse(tag.getId(), tag.getNome());
    }
}
