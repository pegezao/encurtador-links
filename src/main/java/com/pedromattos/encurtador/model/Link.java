package com.pedromattos.encurtador.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;

    @Column(name = "url_original", nullable = false, length = 2048)
    private String urlOriginal;

    @Column(nullable = false)
    private Long cliques = 0L;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "expira_em")
    private LocalDateTime expiraEm;

    protected Link() {
    }

    public Link(String codigo, String urlOriginal, LocalDateTime expiraEm) {
        this.codigo = codigo;
        this.urlOriginal = urlOriginal;
        this.expiraEm = expiraEm;
        this.criadoEm = LocalDateTime.now();
    }

    public boolean estaExpirado() {
        return expiraEm != null && LocalDateTime.now().isAfter(expiraEm);
    }

    public void registrarClique() {
        this.cliques++;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getUrlOriginal() { return urlOriginal; }
    public Long getCliques() { return cliques; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getExpiraEm() { return expiraEm; }
}
