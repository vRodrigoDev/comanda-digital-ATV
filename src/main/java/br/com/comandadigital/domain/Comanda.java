package br.com.comandadigital.domain;

import br.com.comandadigital.domain.enums.StatusComanda;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "comandas")
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusComanda status;

    @Column(nullable = false)
    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @OneToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemComanda> itens = new ArrayList<>();

    protected Comanda() {}

    public Comanda(String nomeCliente, Mesa mesa) {
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        }
        if (mesa == null) {
            throw new IllegalArgumentException("Mesa é obrigatória para abrir comanda.");
        }
        this.nomeCliente = nomeCliente;
        this.mesa = mesa;
        this.status = StatusComanda.ABERTA;
        this.dataAbertura = LocalDateTime.now();
        this.valorTotal = BigDecimal.ZERO;
        this.mesa.ocupar(this);
    }

    public void adicionarItem(Produto produto, int quantidade) {
        validarComandaAberta();
        ItemComanda novoItem = new ItemComanda(this, produto, quantidade);
        this.itens.add(novoItem);
        recalcularTotal();
    }

    public void removerItem(ItemComanda item) {
        validarComandaAberta();
        if (this.itens.remove(item)) {
            recalcularTotal();
        }
    }

    public void fecharComanda() {
        validarComandaAberta();
        this.status = StatusComanda.FECHADA;
        this.dataFechamento = LocalDateTime.now();
        this.mesa.liberar();
    }

    public void cancelarComanda() {
        validarComandaAberta();
        this.status = StatusComanda.CANCELADA;
        this.dataFechamento = LocalDateTime.now();
        this.mesa.liberar();
    }

    private void validarComandaAberta() {
        if (this.status != StatusComanda.ABERTA) {
            throw new IllegalStateException("Operação não permitida: a comanda não está aberta.");
        }
    }

    private void recalcularTotal() {
        this.valorTotal = this.itens.stream()
                .map(ItemComanda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getId() { return id; }
    public String getNomeCliente() { return nomeCliente; }
    public StatusComanda getStatus() { return status; }
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public Mesa getMesa() { return mesa; }
    public List<ItemComanda> getItens() { return Collections.unmodifiableList(itens); }
}
