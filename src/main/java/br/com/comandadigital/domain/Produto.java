package br.com.comandadigital.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(nullable = false)
    private boolean disponivel;

    @ManyToMany
    @JoinTable(
        name = "produto_tags",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagProduto> tags = new HashSet<>();

    protected Produto() {}

    public Produto(String nome, BigDecimal preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
        atualizarPreco(preco);
        this.nome = nome;
        this.disponivel = true;
    }

    public void atualizarPreco(BigDecimal novoPreco) {
        if (novoPreco == null || novoPreco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
        this.preco = novoPreco;
    }

    public void pausarVenda() {
        this.disponivel = false;
    }

    public void reativarVenda() {
        this.disponivel = true;
    }

    public void adicionarTag(TagProduto tag) {
        if (tag != null) {
            this.tags.add(tag);
        }
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public boolean isDisponivel() { return disponivel; }
    public Set<TagProduto> getTags() { return Collections.unmodifiableSet(tags); }
}
