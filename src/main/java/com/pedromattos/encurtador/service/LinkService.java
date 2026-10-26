package com.pedromattos.encurtador.service;

import com.pedromattos.encurtador.dto.CriarLinkRequest;
import com.pedromattos.encurtador.exception.LinkExpiradoException;
import com.pedromattos.encurtador.exception.LinkNaoEncontradoException;
import com.pedromattos.encurtador.model.Link;
import com.pedromattos.encurtador.repository.LinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class LinkService {

    private static final String CARACTERES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TAMANHO_CODIGO = 6;

    private final LinkRepository repository;
    private final SecureRandom random = new SecureRandom();

    public LinkService(LinkRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Link criar(CriarLinkRequest request) {
        String codigo = gerarCodigoUnico();
        LocalDateTime expiraEm = request.diasValidade() == null
                ? null
                : LocalDateTime.now().plusDays(request.diasValidade());
        return repository.save(new Link(codigo, request.url(), expiraEm));
    }

    @Transactional
    public String acessar(String codigo) {
        Link link = buscar(codigo);
        if (link.estaExpirado()) {
            throw new LinkExpiradoException(codigo);
        }
        link.registrarClique();
        return link.getUrlOriginal();
    }

    @Transactional(readOnly = true)
    public Link buscar(String codigo) {
        return repository.findByCodigo(codigo)
                .orElseThrow(() -> new LinkNaoEncontradoException(codigo));
    }

    private String gerarCodigoUnico() {
        String codigo;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < TAMANHO_CODIGO; i++) {
                sb.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
            }
            codigo = sb.toString();
        } while (repository.existsByCodigo(codigo));
        return codigo;
    }
}
