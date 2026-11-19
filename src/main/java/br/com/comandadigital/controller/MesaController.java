package br.com.comandadigital.controller;

import br.com.comandadigital.domain.Mesa;
import br.com.comandadigital.service.MesaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    public record CriarMesaRequest(Integer numero) {}

    @PostMapping
    public ResponseEntity<Mesa> cadastrar(@RequestBody CriarMesaRequest request) {
        Mesa mesaSalva = mesaService.cadastrar(request.numero());
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaSalva);
    }

    @GetMapping
    public ResponseEntity<List<Mesa>> listarTodas() {
        return ResponseEntity.ok(mesaService.listarTodas());
    }

    @GetMapping("/livres")
    public ResponseEntity<List<Mesa>> listarLivres() {
        return ResponseEntity.ok(mesaService.listarLivres());
    }

    @GetMapping("/{numero}")
    public ResponseEntity<Mesa> buscarPorNumero(@PathVariable Integer numero) {
        return ResponseEntity.ok(mesaService.buscarPorNumero(numero));
    }
}