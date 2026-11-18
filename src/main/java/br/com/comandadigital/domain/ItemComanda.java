package br.com.comandadigital.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_comanda")
public class ItemComanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitarioRegistrado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comanda_id")
    private Comanda comanda;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    protected ItemComanda() {}

    public ItemComanda(Comanda comanda, Produto produto, int quantidade) {
        if (produto == null || !produto.isDisponivel()) {
            throw new IllegalStateException("Produto indisponível para pedido.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.comanda = comanda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitarioRegistrado = produto.getPreco();
    }

    public void alterarQuantidade(int novaQuantidade) {
        if (novaQuantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.quantidade = novaQuantidade;
    }

    public BigDecimal getSubtotal() {
        return precoUnitarioRegistrado.multiply(BigDecimal.valueOf(quantidade));
    }

    public Long getId() { return id; }
    public Integer getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitarioRegistrado() { return precoUnitarioRegistrado; }
    public Comanda getComanda() { return comanda; }
    public Produto getProduto() { return produto; }
}
