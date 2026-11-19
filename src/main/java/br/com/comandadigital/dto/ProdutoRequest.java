package br.com.comandadigital.dto;

import java.math.BigDecimal;

public record ProdutoRequest(String nome, BigDecimal preco) {
}
