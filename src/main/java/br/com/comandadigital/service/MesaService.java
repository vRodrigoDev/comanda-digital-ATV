package br.com.comandadigital.service;

import br.com.comandadigital.domain.Mesa;
import br.com.comandadigital.repository.MesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Transactional
    public Mesa cadastrar(Integer numero) {
        if (numero == null || numero <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O número da mesa deve ser positivo.");
        }

        if (mesaRepository.existsByNumero(numero)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma mesa cadastrada com o número " + numero);
        }

        Mesa novaMesa = new Mesa(numero);
        return mesaRepository.save(novaMesa);
    }

    @Transactional(readOnly = true)
    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Mesa> listarLivres() {
        return mesaRepository.findByOcupadaFalse();
    }

    @Transactional(readOnly = true)
    public Mesa buscarPorNumero(Integer numero) {
        return mesaRepository.findByNumero(numero)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Mesa número " + numero + " não encontrada."
                ));
    }
}