package com.pedromattos.encurtador.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCriarRedirecionarEContarClique() throws Exception {
        MvcResult resultado = mockMvc.perform(post("/api/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"https://github.com/Pegezao\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo").exists())
                .andReturn();

        String corpo = resultado.getResponse().getContentAsString();
        String codigo = corpo.replaceAll(".*\"codigo\":\"([^\"]+)\".*", "$1");

        mockMvc.perform(get("/" + codigo))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://github.com/Pegezao"));

        mockMvc.perform(get("/api/links/" + codigo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliques").value(1));
    }

    @Test
    void deveRecusarUrlInvalida() throws Exception {
        mockMvc.perform(post("/api/links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"isso-nao-e-url\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").exists());
    }

    @Test
    void deveRetornar404ParaCodigoInexistente() throws Exception {
        mockMvc.perform(get("/naoexiste"))
                .andExpect(status().isNotFound());
    }
}
