README: |
# 📱 Plataforma de Comunicação - Magalu

Este projeto é um desafio técnico proposto pela equipe do Magalu, com o objetivo de implementar uma plataforma de comunicação que permita o agendamento de envios por diferentes canais.

  ---

## 🧾 Escopo do Desafio

> O Magalu tem o desafio de desenvolver uma plataforma de comunicação. Você foi escolhido(a) para iniciar o desenvolvimento da primeira sprint.

### ✅ Funcionalidades desenvolvidas:

- [x] Endpoint para **agendamento** de comunicações (Email, SMS, Push, WhatsApp)
- [x] Endpoint para **consultar** o status de um agendamento por ID
- [x] Endpoint para **remover** um agendamento
- [x] Estrutura de banco pensada para facilitar a **etapa de envio futura**
- [x] Script SQL de inicialização com carga de dados de teste
- [x] Documentação interativa da API via **Swagger**

  ---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.4.4**
- **PostgreSQL 14**
- **Docker & Docker Compose**
- **Lombok**
- **Spring Data JPA**
- **Bean Validation**
- **Swagger (Springdoc OpenAPI v2)**

  ---

## 🐳 Como subir a aplicação com Docker

Este projeto já vem configurado com `Dockerfile` e `docker-compose.yml` para facilitar o setup.

### ✅ Pré-requisitos

- [Docker](https://www.docker.com/products/docker-desktop/) instalado (versão Desktop para Windows/macOS ou CLI no Linux)

### 📆 Subindo tudo com um único comando:

  ```bash
  docker-compose up --build
  ```

Esse comando irá:

1. Criar a imagem da aplicação com Maven
2. Subir um banco de dados PostgreSQL com script de inicialização (`init.sql`)
3. Iniciar a aplicação Spring Boot na porta `8080`

  ---

### 🧼 Limpando os containers e volumes:

  ```bash
  docker-compose down -v
  ```

Isso derruba tudo e remove os dados salvos no banco.

  ---

## 🚀 Acessando a API

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Base URL da API**: `http://localhost:8080/agendamentos`

  ---

## 💃 Estrutura do Projeto

  ```
  communication-test
  ├── src
  │   └── main/java/com/luizalabs/communication/
  │       ├── controller/
  │       ├── service/
  │       ├── repository/
  │       ├── model/
  │       ├── dto/
  │       └── mapper/
  ├── docker/
  │   └── db/
  │       └── init.sql
  ├── docker-compose.yml
  ├── Dockerfile
  └── README.md
  ```

  ---

## 🥪 Testes com dados prontos

Após subir o projeto com Docker, o banco já terá dois registros de agendamentos prontos.

### Exemplo de retorno em `GET /agendamentos/1`

  ```json
  {
    "id": 1,
    "dataHoraEnvio": "2025-03-23T10:00:00",
    "destinatario": "teste1@magalu.com",
    "mensagem": "Mensagem de teste 1",
    "tipo": "EMAIL",
    "status": "PENDENTE",
    "criadoEm": "2025-03-22T18:30:00"
  }
  ```

  ---

## 📂 Banco de Dados

- **Banco**: `comunicacao_db`
- **Usuário**: `comunicacao`
- **Senha**: `comunicacao123`
- **Porta**: `5433` (host) → `5432` (container)

Você pode acessar via DBeaver, PgAdmin ou qualquer cliente PostgreSQL.

  ---

## 📉 Observações Técnicas

- A estrutura foi pensada para o futuro processo de envio:
    - O sistema de envio pode buscar agendamentos com `status = 'PENDENTE'` e `dataHoraEnvio <= NOW()`
    - O status poderá ser atualizado para `ENVIADO` ou `FALHA`, com campos de auditoria (`enviadoEm`, `erroEnvio`)
- A validação dos campos é feita com `Bean Validation`
- Os DTOs isolam a camada de domínio do modelo REST
- Lombok é usado para reduzir boilerplate de getters/setters

  ---

## 📬 Contato

Feito com 💙 para o desafio técnico do Magalu.

Caso precise entrar em contato:

- **Nome**: Evander Lucena
- **Email**: evander.willian@gmail.com
- **GitHub**: [https://github.com/EvanderLucena](https://github.com/EvanderLucena)
