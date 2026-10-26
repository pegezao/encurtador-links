package com.pedromattos.encurtador.controller;

import com.pedromattos.encurtador.dto.CriarLinkRequest;
import com.pedromattos.encurtador.dto.LinkResponse;
import com.pedromattos.encurtador.model.Link;
import com.pedromattos.encurtador.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class LinkController {

    private final LinkService service;
    private final String baseUrl;

    public LinkController(LinkService service, @Value("${app.base-url}") String baseUrl) {
        this.service = service;
        this.baseUrl = baseUrl;
    }

    @PostMapping("/api/links")
    public ResponseEntity<LinkResponse> criar(@RequestBody @Valid CriarLinkRequest request) {
        Link link = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(LinkResponse.de(link, baseUrl));
    }

    @GetMapping("/api/links/{codigo}")
    public LinkResponse estatisticas(@PathVariable String codigo) {
        return LinkResponse.de(service.buscar(codigo), baseUrl);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Void> redirecionar(@PathVariable String codigo) {
        String url = service.acessar(codigo);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }
}
