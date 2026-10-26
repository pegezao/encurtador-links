# 🔗 Encurtador de Links

[![CI](https://github.com/Pegezao/encurtador-links/actions/workflows/ci.yml/badge.svg)](https://github.com/Pegezao/encurtador-links/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)

API REST para encurtar URLs, redirecionar e acompanhar quantos cliques cada link recebeu, com validade opcional por link.

## ✨ Funcionalidades

- Gera um código curto e único de 6 caracteres para qualquer URL
- Redireciona para a URL original (HTTP 302) e contabiliza cada clique
- Links com validade opcional em dias (link expirado retorna HTTP 410)
- Validação de entrada e respostas de erro padronizadas em JSON
- Documentação interativa com Swagger UI
- Migrações de banco versionadas com Flyway
- Testes de unidade e de integração rodando no GitHub Actions a cada push

## 🛠️ Tecnologias

| Camada | Ferramentas |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 4 (Web MVC, Data JPA, Validation) |
| Banco de dados | PostgreSQL 17 (H2 em memória para desenvolvimento) |
| Migrações | Flyway |
| Documentação | springdoc-openapi (Swagger UI) |
| Testes | JUnit 5, Mockito, MockMvc |
| Infra | Docker Compose, GitHub Actions |

## 🏗️ Arquitetura

```
controller  →  service  →  repository  →  banco
    ↑             ↑
   dto        exception
```

- **controller**: expõe os endpoints HTTP
- **service**: regras de negócio (geração de código, expiração, contagem de cliques)
- **repository**: acesso a dados com Spring Data JPA
- **dto**: objetos de entrada e saída da API, separados da entidade
- **exception**: erros de domínio e tratamento global com `@RestControllerAdvice`

## 📡 Endpoints

| Método | Rota | Descrição | Respostas |
|---|---|---|---|
| `POST` | `/api/links` | Cria um link curto | 201, 400 |
| `GET` | `/{codigo}` | Redireciona e conta um clique | 302, 404, 410 |
| `GET` | `/api/links/{codigo}` | Dados e estatísticas do link | 200, 404 |

### Exemplo

```bash
curl -X POST http://localhost:8080/api/links \
  -H "Content-Type: application/json" \
  -d '{"url": "https://github.com/Pegezao", "diasValidade": 7}'
```

```json
{
  "codigo": "pkYiSx",
  "urlCurta": "http://localhost:8080/pkYiSx",
  "urlOriginal": "https://github.com/Pegezao",
  "cliques": 0,
  "criadoEm": "2026-10-26T11:55:37",
  "expiraEm": "2026-11-02T11:55:37"
}
```

## 🚀 Como rodar

Pré-requisito: Java 21 ou superior. Não precisa instalar o Maven, o projeto usa o Maven Wrapper.

```bash
git clone https://github.com/Pegezao/encurtador-links.git
cd encurtador-links
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080` e a documentação em `http://localhost:8080/swagger-ui.html`.

### Com PostgreSQL (Docker)

```bash
docker compose up -d
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

## 🧪 Testes

```bash
./mvnw test
```

## 🗺️ Próximos passos

- [ ] Deploy público
- [ ] Cache dos redirecionamentos com Redis
- [ ] Limite de requisições por IP
- [ ] Autenticação com JWT para cada usuário gerenciar seus links

## 👤 Autor

**Pedro Mattos**: [GitHub](https://github.com/Pegezao) · [LinkedIn](https://www.linkedin.com/in/pedrogmattos)
