package br.com.comandadigital.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags_produto")
public class TagProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "tags")
    private Set<Produto> produtos = new HashSet<>();

    protected TagProduto() {}

    public TagProduto(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da tag é obrigatório.");
        }
        this.nome = nome.trim().toUpperCase();
    }

    public void renomear(String novoNome) {
        if (novoNome == null || novoNome.isBlank()) {
            throw new IllegalArgumentException("Novo nome inválido.");
        }
        this.nome = novoNome.trim().toUpperCase();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
}
