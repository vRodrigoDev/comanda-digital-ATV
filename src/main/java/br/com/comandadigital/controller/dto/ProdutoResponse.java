package br.com.comandadigital.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoResponse(Long id, String nome, BigDecimal preco, boolean disponivel, List<String> tags) {
}
