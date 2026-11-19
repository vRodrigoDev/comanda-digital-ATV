package br.com.comandadigital.service;

import br.com.comandadigital.domain.Produto;
import br.com.comandadigital.domain.TagProduto;
import br.com.comandadigital.exception.ResourceNotFoundException;
import br.com.comandadigital.repository.ProdutoRepository;
import br.com.comandadigital.repository.TagProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CatalogoService {

    private final ProdutoRepository produtoRepository;
    private final TagProdutoRepository tagRepository;

    public CatalogoService(ProdutoRepository produtoRepository, TagProdutoRepository tagRepository) {
        this.produtoRepository = produtoRepository;
        this.tagRepository = tagRepository;
    }

    public Produto criarProduto(String nome, BigDecimal preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório.");
        }
        return produtoRepository.save(new Produto(nome.trim(), preco));
    }

    @Transactional(readOnly = true)
    public List<Produto> listarDisponiveis() {
        return produtoRepository.findByDisponivelTrue();
    }

    @Transactional(readOnly = true)
    public Produto buscarProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto " + id + " não encontrado."));
    }

    public Produto pausarVenda(Long id) {
        Produto produto = buscarProduto(id);
        produto.pausarVenda();
        return produtoRepository.save(produto);
    }

    public Produto adicionarTag(Long id, String nomeTag) {
        if (nomeTag == null || nomeTag.isBlank()) {
            throw new IllegalArgumentException("Nome da tag é obrigatório.");
        }
        Produto produto = buscarProduto(id);
        TagProduto tag = obterOuCriarTag(nomeTag);
        produto.adicionarTag(tag);
        return produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public List<TagProduto> listarTags() {
        return tagRepository.findAll();
    }

    public TagProduto criarTag(String nome) {
        return obterOuCriarTag(nome);
    }

    private TagProduto obterOuCriarTag(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da tag é obrigatório.");
        }
        String nomeNormalizado = nome.trim().toUpperCase();
        return tagRepository.findByNomeIgnoreCase(nomeNormalizado)
                .orElseGet(() -> tagRepository.save(new TagProduto(nomeNormalizado)));
    }
}
