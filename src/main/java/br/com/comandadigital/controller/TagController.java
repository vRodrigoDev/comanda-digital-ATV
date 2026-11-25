package br.com.comandadigital.controller;

import br.com.comandadigital.domain.TagProduto;
import br.com.comandadigital.dto.TagRequest;
import br.com.comandadigital.dto.TagResponse;
import br.com.comandadigital.service.CatalogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final CatalogoService catalogoService;

    public TagController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @PostMapping
    public ResponseEntity<TagResponse> criarTag(@RequestBody TagRequest request) {
        TagProduto salva = catalogoService.criarTag(request.nome());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salva.getId())
                .toUri();
        return ResponseEntity.created(location).body(TagResponse.from(salva));
    }

    @GetMapping
    public List<TagResponse> listarTags() {
        return catalogoService.listarTags().stream()
                .map(TagResponse::from)
                .toList();
    }
}
