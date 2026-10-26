# Encurtador de Links

[![CI](https://github.com/pegezao/encurtador-links/actions/workflows/ci.yml/badge.svg)](https://github.com/pegezao/encurtador-links/actions/workflows/ci.yml)

API REST para encurtar URLs e acompanhar quantos cliques cada link recebeu. Fiz esse projeto pra praticar Spring Boot, banco de dados e testes.

## Tecnologias

- Java 21 e Spring Boot 4
- Spring Data JPA e PostgreSQL (H2 para rodar localmente)
- Flyway para as migrações
- Swagger para documentação
- JUnit, Mockito e MockMvc nos testes
- GitHub Actions rodando os testes a cada push

## Endpoints

| Método | Rota | O que faz |
|---|---|---|
| POST | `/api/links` | Cria um link curto |
| GET | `/{codigo}` | Redireciona para a URL original e conta o clique |
| GET | `/api/links/{codigo}` | Mostra os dados e os cliques do link |

O campo `diasValidade` é opcional. Se o link expirar, a API retorna 410.

```bash
curl -X POST http://localhost:8080/api/links \
  -H "Content-Type: application/json" \
  -d '{"url": "https://github.com/pegezao", "diasValidade": 7}'
```

## Como rodar

```bash
git clone https://github.com/pegezao/encurtador-links.git
cd encurtador-links
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080` e o Swagger fica em `http://localhost:8080/swagger-ui.html`.

Para usar o PostgreSQL:

```bash
docker compose up -d
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

Testes:

```bash
./mvnw test
```

## Próximos passos

- Deploy
- Cache com Redis
- Login com JWT
