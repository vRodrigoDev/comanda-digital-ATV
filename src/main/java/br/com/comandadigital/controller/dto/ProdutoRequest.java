package br.com.comandadigital.controller.dto;

import java.math.BigDecimal;

public record ProdutoRequest(String nome, BigDecimal preco) {
}
