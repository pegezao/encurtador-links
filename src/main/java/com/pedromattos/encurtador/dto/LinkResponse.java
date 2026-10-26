package com.pedromattos.encurtador.dto;

import com.pedromattos.encurtador.model.Link;

import java.time.LocalDateTime;

public record LinkResponse(
        String codigo,
        String urlCurta,
        String urlOriginal,
        Long cliques,
        LocalDateTime criadoEm,
        LocalDateTime expiraEm
) {
    public static LinkResponse de(Link link, String baseUrl) {
        return new LinkResponse(
                link.getCodigo(),
                baseUrl + "/" + link.getCodigo(),
                link.getUrlOriginal(),
                link.getCliques(),
                link.getCriadoEm(),
                link.getExpiraEm()
        );
    }
}
