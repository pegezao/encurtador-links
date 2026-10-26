package com.pedromattos.encurtador.service;

import com.pedromattos.encurtador.dto.CriarLinkRequest;
import com.pedromattos.encurtador.exception.LinkNaoEncontradoException;
import com.pedromattos.encurtador.model.Link;
import com.pedromattos.encurtador.repository.LinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LinkServiceTest {

    private LinkRepository repository;
    private LinkService service;

    @BeforeEach
    void setUp() {
        repository = mock(LinkRepository.class);
        service = new LinkService(repository);
        when(repository.save(any(Link.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void deveCriarLinkComCodigoDeSeisCaracteres() {
        Link link = service.criar(new CriarLinkRequest("https://github.com/Pegezao", null));

        assertThat(link.getCodigo()).hasSize(6);
        assertThat(link.getUrlOriginal()).isEqualTo("https://github.com/Pegezao");
        assertThat(link.getExpiraEm()).isNull();
    }

    @Test
    void deveContarCliqueAoAcessar() {
        Link link = new Link("abc123", "https://google.com", null);
        when(repository.findByCodigo("abc123")).thenReturn(Optional.of(link));

        String url = service.acessar("abc123");

        assertThat(url).isEqualTo("https://google.com");
        assertThat(link.getCliques()).isEqualTo(1L);
    }

    @Test
    void deveLancarErroQuandoCodigoNaoExiste() {
        when(repository.findByCodigo("naoexiste")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.acessar("naoexiste"))
                .isInstanceOf(LinkNaoEncontradoException.class);
    }
}
