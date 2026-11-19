package br.com.comandadigital.service;

import br.com.comandadigital.controller.dto.ProdutoResponse;
import br.com.comandadigital.domain.Produto;
import br.com.comandadigital.domain.TagProduto;
import br.com.comandadigital.repository.ProdutoRepository;
import br.com.comandadigital.repository.TagProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final TagProdutoRepository tagProdutoRepository;

    public ProdutoService(ProdutoRepository produtoRepository, TagProdutoRepository tagProdutoRepository) {
        this.produtoRepository = produtoRepository;
        this.tagProdutoRepository = tagProdutoRepository;
    }

    public ProdutoResponse cadastrarProduto(String nome, BigDecimal preco) {
        Produto produto = produtoRepository.save(new Produto(nome, preco));
        return toDto(produto);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listarCardapio() {
        return produtoRepository.findByDisponivelTrueOrderByNomeAsc().stream().map(this::toDto).toList();
    }

    public ProdutoResponse pausarVenda(Long id) {
        Produto produto = findById(id);
        produto.pausarVenda();
        return toDto(produto);
    }

    public ProdutoResponse reativarVenda(Long id) {
        Produto produto = findById(id);
        produto.reativarVenda();
        return toDto(produto);
    }

    public ProdutoResponse associarTags(Long produtoId, List<String> nomes) {
        Produto produto = findById(produtoId);
        Set<String> normalizados = new HashSet<>();
        if (nomes != null) {
            for (String nome : nomes) {
                String normalizado = normalizarNome(nome);
                if (normalizado.isEmpty() || !normalizados.add(normalizado)) {
                    continue;
                }
                TagProduto tag = tagProdutoRepository.findByNome(normalizado);
                if (tag == null) {
                    tag = tagProdutoRepository.save(new TagProduto(normalizado));
                }
                produto.adicionarTag(tag);
            }
        }
        produtoRepository.save(produto);
        return toDto(produto);
    }

    private Produto findById(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    private String normalizarNome(String nome) {
        return nome == null ? "" : nome.trim().toUpperCase();
    }

    private ProdutoResponse toDto(Produto produto) {
        List<String> tags = produto.getTags().stream().map(TagProduto::getNome).sorted().toList();
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getPreco(), produto.isDisponivel(), tags);
    }
}
