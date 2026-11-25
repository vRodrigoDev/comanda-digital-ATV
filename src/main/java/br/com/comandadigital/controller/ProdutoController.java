package br.com.comandadigital.controller;

import br.com.comandadigital.controller.dto.AssociarTagsRequest;
import br.com.comandadigital.controller.dto.ProdutoRequest;
import br.com.comandadigital.controller.dto.ProdutoResponse;
import br.com.comandadigital.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrarProduto(@RequestBody ProdutoRequest request) {
        ProdutoResponse dto = produtoService.cadastrarProduto(request.nome(), request.preco());
        return ResponseEntity.created(URI.create("/produtos/" + dto.id())).body(dto);
    }

    @GetMapping
    public List<ProdutoResponse> listarCardapio() {
        return produtoService.listarCardapio();
    }

    @PatchMapping("/{id}/pausar")
    public ProdutoResponse pausarVenda(@PathVariable Long id) {
        return produtoService.pausarVenda(id);
    }

    @PatchMapping("/{id}/reativar")
    public ProdutoResponse reativarVenda(@PathVariable Long id) {
        return produtoService.reativarVenda(id);
    }

    @PostMapping("/{id}/tags")
    public ProdutoResponse associarTags(@PathVariable Long id, @RequestBody AssociarTagsRequest request) {
        return produtoService.associarTags(id, request.nomes());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> tratarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
