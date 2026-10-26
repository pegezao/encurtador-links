package com.pedromattos.encurtador.controller;

import com.pedromattos.encurtador.dto.CriarLinkRequest;
import com.pedromattos.encurtador.dto.LinkResponse;
import com.pedromattos.encurtador.model.Link;
import com.pedromattos.encurtador.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Tag(name = "Links", description = "Criacao, redirecionamento e estatisticas de links")
public class LinkController {

    private final LinkService service;
    private final String baseUrl;

    public LinkController(LinkService service, @Value("${app.base-url}") String baseUrl) {
        this.service = service;
        this.baseUrl = baseUrl;
    }

    @Operation(summary = "Cria um link curto")
    @PostMapping("/api/links")
    public ResponseEntity<LinkResponse> criar(@RequestBody @Valid CriarLinkRequest request) {
        Link link = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(LinkResponse.de(link, baseUrl));
    }

    @Operation(summary = "Mostra os dados e os cliques de um link")
    @GetMapping("/api/links/{codigo}")
    public LinkResponse estatisticas(@PathVariable String codigo) {
        return LinkResponse.de(service.buscar(codigo), baseUrl);
    }

    @Operation(summary = "Redireciona para a URL original e conta um clique")
    @GetMapping("/{codigo:[a-zA-Z0-9]{6}}")
    public ResponseEntity<Void> redirecionar(@PathVariable String codigo) {
        String url = service.acessar(codigo);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }
}
