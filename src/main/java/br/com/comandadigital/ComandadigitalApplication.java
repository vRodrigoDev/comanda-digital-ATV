package br.com.comandadigital;

import br.com.comandadigital.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ComandadigitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComandadigitalApplication.class, args);
    }

    @Bean
    CommandLineRunner testarDominio() {
        return args -> {
            System.out.println("\n=== INICIANDO TESTE DO DOMINIO COMANDA DIGITAL ===");

            Mesa mesa5 = new Mesa(5);
            Produto peixada = new Produto("Peixada Completa", new BigDecimal("85.00"));
            Produto cerveja = new Produto("Cerveja 600ml", new BigDecimal("14.00"));
            TagProduto tagBebida = new TagProduto("Bebida Gelada");
            cerveja.adicionarTag(tagBebida);

            System.out.println("Mesa " + mesa5.getNumero() + " ocupada? " + mesa5.isOcupada());

            Comanda comanda = new Comanda("Vitor", mesa5);
            System.out.println("Comanda aberta para: " + comanda.getNomeCliente() + " | Status: " + comanda.getStatus());
            System.out.println("Mesa " + mesa5.getNumero() + " ocupada agora? " + mesa5.isOcupada());

            comanda.adicionarItem(peixada, 1);
            comanda.adicionarItem(cerveja, 3);
            System.out.println("Total da comanda calculado: R$ " + comanda.getValorTotal());

            comanda.fecharComanda();
            System.out.println("Status apos fechar: " + comanda.getStatus());
            System.out.println("Mesa " + mesa5.getNumero() + " ocupada apos fechar? " + mesa5.isOcupada());

            try {
                comanda.adicionarItem(cerveja, 1);
            } catch (IllegalStateException e) {
                System.out.println("BLOQUEIO FUNCIONANDO: " + e.getMessage());
            }

            System.out.println("=== TESTE CONCLUIDO COM SUCESSO ===\n");
        };
    }
}
