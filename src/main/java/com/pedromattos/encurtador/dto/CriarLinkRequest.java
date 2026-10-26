package com.pedromattos.encurtador.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CriarLinkRequest(
        @NotBlank(message = "A URL e obrigatoria")
        @Pattern(regexp = "^https?://.+", message = "A URL deve comecar com http:// ou https://")
        String url,

        @Min(value = 1, message = "A validade deve ser de pelo menos 1 dia")
        Integer diasValidade
) {
}
