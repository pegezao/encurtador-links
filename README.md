# Encurtador de Links

API REST para encurtar URLs e acompanhar quantos cliques cada link recebeu.

## Tecnologias

Java 21, Spring Boot 4, Spring Data JPA, Flyway, PostgreSQL (H2 para desenvolvimento), JUnit 5, Mockito, Docker Compose e GitHub Actions.

## Endpoints

| Metodo | Rota | Descricao |
|---|---|---|
| POST | `/api/links` | Cria um link curto. Corpo: `{"url": "https://...", "diasValidade": 7}` (`diasValidade` e opcional) |
| GET | `/{codigo}` | Redireciona para a URL original e conta um clique |
| GET | `/api/links/{codigo}` | Mostra os dados e o numero de cliques do link |

## Como rodar

```bash
./mvnw spring-boot:run
```

Com PostgreSQL via Docker:

```bash
docker compose up -d
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

## Testes

```bash
./mvnw test
```
