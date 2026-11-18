package br.com.comandadigital.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer numero;

    @Column(nullable = false)
    private boolean ocupada;

    @OneToOne(mappedBy = "mesa")
    private Comanda comandaAtual;

    protected Mesa() {}

    public Mesa(Integer numero) {
        if (numero == null || numero <= 0) {
            throw new IllegalArgumentException("Número da mesa deve ser positivo.");
        }
        this.numero = numero;
        this.ocupada = false;
    }

    public void ocupar(Comanda comanda) {
        if (this.ocupada) {
            throw new IllegalStateException("A mesa " + this.numero + " já está ocupada.");
        }
        this.ocupada = true;
        this.comandaAtual = comanda;
    }

    public void liberar() {
        this.ocupada = false;
        this.comandaAtual = null;
    }

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public boolean isOcupada() { return ocupada; }
    public Comanda getComandaAtual() { return comandaAtual; }
}
