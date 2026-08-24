# Hora do Prato

> API REST de gestão de usuários para a Fase 1 do Tech Challenge da Pós-Graduação FIAP em Arquitetura e Desenvolvimento Java.

Projeto desenvolvido com **Java 21 + Spring Boot 4.0.7**, com foco na gestão de usuários, autenticação, validação de credenciais, atualização de dados, troca de senha, busca por nome e exclusão de usuários.

## Sumário

- [Características](#características)
- [Tecnologias](#tecnologias)
- [Requisitos](#requisitos)
- [Execução com Docker Compose](#execução-com-docker-compose)
- [Execução local](#execução-local)
- [API Endpoints](#api-endpoints)
- [Contrato OpenAPI / Swagger](#contrato-openapi--swagger)
- [Coleção Postman](#coleção-postman)
- [Tratamento de erros](#tratamento-de-erros)
- [Estrutura do projeto](#estrutura-do-projeto)

## Características

- Cadastro de usuários;
- Dois tipos de usuário: `CLIENTE` e `DONO_RESTAURANTE`;
- Validação de login e senha;
- Alteração de senha em endpoint exclusivo;
- Atualização das demais informações em endpoint distinto;
- Registro automático da data da última alteração;
- Busca de usuários por nome;
- Unicidade de login e e-mail no cadastro;
- API versionada em `/api/v1`;
- Documentação OpenAPI / Swagger;
- Persistência em MySQL 8.0;
- Docker Compose para aplicação + banco;
- Respostas de erro padronizadas com `ProblemDetail` quando tratadas pelo `GlobalExceptionHandler`.

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 4.0.7 | Framework |
| Spring Data JPA | 4.0.7 | Persistência |
| MySQL | 8.0 | Banco relacional |
| Springdoc OpenAPI | 2.8.0 | Swagger / documentação |
| OpenAPI Generator | 7.4.0 | Geração de interfaces e DTOs a partir do contrato |
| Maven | 3.9+ | Build |
| Docker / Docker Compose | 20.10+ / 2.0+ | Containerização |
| JUnit 5 / Spring Boot Test | — | Testes automatizados |

## Requisitos

### Execução com Docker

- Docker 20.10+
- Docker Compose 2.0+

### Execução local

- Java 21 JDK
- Maven 3.9+
- MySQL 8.0+ acessível em `localhost:3306`

## Execução com Docker Compose

A forma recomendada de execução é pelo Docker Compose. O arquivo `docker-compose.yml` cria dois serviços:

- `db`: MySQL 8.0;
- `app`: aplicação Spring Boot.

O banco possui healthcheck e a aplicação aguarda o banco ficar saudável antes de iniciar.

### Subir a aplicação

```bash
docker compose up --build
```

Aplicação:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

### Configuração do banco

```text
Database: meu_banco
User: java_user
Password: javapassword
Root password: rootpassword
```

O Compose injeta as propriedades por meio de:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

O `application.yaml` possui valores padrão para execução local.

## Execução local

Com MySQL disponível em `localhost:3306`:

```bash
mvn clean package
mvn spring-boot:run
```

## API Endpoints

Base URL:

```text
http://localhost:8080/api/v1
```

### Autenticação

```http
POST /auth/login
Content-Type: application/json
```

Request:

```json
{
  "login": "joao_silva_001",
  "senha": "SenhaSegura@123"
}
```

Sucesso: `200 OK` com mensagem de confirmação.

Credenciais inválidas: `401 Unauthorized` com `ProblemDetail`.

### Cadastro de usuário

```http
POST /usuarios/cadastrar
```

Sucesso: `201 Created`.

Duplicidade de login ou e-mail: `409 Conflict` com `ProblemDetail`.

### Alteração de senha

```http
PATCH /usuarios/{id}/senha
```

Sucesso: `204 No Content`.

Regra de negócio violada: `409 Conflict` com `ProblemDetail`.

Usuário inexistente: `404 Not Found` com `ProblemDetail`.

### Atualização de dados

```http
PATCH /usuarios/{id}/atualizar
```

Sucesso: `204 No Content`.

Usuário inexistente: `404 Not Found` com `ProblemDetail`.

> O contrato atual permite atualizar nome, tipo de usuário e endereço, conforme implementado no serviço.

### Busca por nome

```http
GET /usuarios/buscar?nome=João
```

Sucesso: `200 OK` com uma lista de usuários.

### Deleção

```http
DELETE /usuarios/{id}
```

Sucesso: `204 No Content`.

Usuário inexistente: `404 Not Found` com `ProblemDetail`.

## Contrato OpenAPI / Swagger

O contrato versionado da API está em:

```text
src/main/resources/api/usuarios-api.yaml
```

O projeto utiliza OpenAPI Generator para gerar as interfaces e DTOs da API durante o build Maven.

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

Documentação JSON:

```text
http://localhost:8080/v3/api-docs
```

As respostas de erro estão documentadas como `application/problem+json` por meio do schema `ProblemDetail`.

## Coleção Postman

A coleção obrigatória do Tech Challenge está versionada no projeto em:

```text
postman-collection.json
```

Ela cobre os principais cenários exigidos pelo enunciado:

### Cadastro

- Cadastro válido - Cliente;
- Cadastro válido - Dono Restaurante;
- E-mail duplicado;
- Login duplicado;
- Campos obrigatórios faltando;
- Tipo de usuário inválido, validando o comportamento atual de erro `500`.

### Autenticação

- Login válido;
- Login com senha incorreta;
- Login com usuário inexistente.

### Senha

- Alteração com sucesso;
- Senha antiga incorreta;
- Nova senha igual à anterior;
- Usuário inexistente.

### Atualização

- Atualização com sucesso;
- Usuário inexistente;
- Tipo de usuário inválido, validando o comportamento atual de erro `500`.

### Busca

- Busca por nome completo;
- Busca por nome parcial;
- Nenhum resultado.

### Deleção

- Deleção com sucesso;
- Usuário inexistente.

A variável `base_url` já vem configurada para `http://localhost:8080`. Os testes de cadastro armazenam os IDs necessários para os cenários seguintes.

## Tratamento de erros

A aplicação utiliza `org.springframework.http.ProblemDetail` no `GlobalExceptionHandler`.

| Status | Situação |
|---|---|
| 200 | Login válido / busca |
| 201 | Cadastro realizado |
| 204 | Atualização de dados, troca de senha e deleção |
| 400 | Erros de validação de entrada tratados pelo Spring |
| 401 | Credenciais de login inválidas |
| 404 | Usuário não encontrado |
| 409 | Conflitos de cadastro e regras de negócio tratadas pelas exceções específicas |
| 500 | Exceções não tratadas pelos handlers específicos, incluindo determinados erros de desserialização do enum |

## Estrutura do projeto

```text
src/main/java/br/com/fiap/horadoprato/horadoprato/
├── controller/                 # Controllers REST
├── controller/exception/      # Tratamento global de erros
├── services/                   # Regras e orquestração da aplicação
├── repositories/               # Acesso ao banco
├── model/                      # Entidades e value objects utilizados pelo domínio
├── dto/                        # Modelos gerados pelo contrato OpenAPI e exceções
└── infra/security/             # Criptografia utilizada atualmente

src/main/resources/
├── api/usuarios-api.yaml       # Contrato OpenAPI
└── application.yaml            # Configuração da aplicação

postman-collection.json         # Coleção de testes Postman
docker-compose.yml              # Orquestração app + MySQL
Dockerfile                      # Build e runtime da aplicação
README.md                       # Documentação do projeto
```

## Observações

- O projeto da Fase 1 está concentrado em gestão de usuários e autenticação.
- Spring Security com JWT não faz parte da implementação obrigatória desta fase.
- A configuração de infraestrutura foi organizada para execução reproduzível por Docker Compose.

---

**Última atualização:** Agosto de 2026
