# Hora do Prato

> Sistema de gestão de usuários para plataforma de reservas em restaurantes

Projeto desenvolvido com **Java 21 + Spring Boot 4.0.7** para solucionar o problema de um grupo de restaurantes. Este sistema permitirá que os clientes escolham restaurantes com base na comida oferecida, em vez de se basearem na qualidade do sistema de gestão.

## 📋 Sumário

- [Características](#características)
- [Tecnologias](#tecnologias)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Como Executar](#como-executar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [API Endpoints](#api-endpoints)
- [Coleção Postman](#coleção-postman)
- [Configuração de Ambiente](#configuração-de-ambiente)
- [Documentação](#documentação)
- [Contribuição](#contribuição)

## ✨ Características

- ✅ Cadastro e autenticação de usuários
- ✅ Dois tipos de usuário: `CLIENTE` e `DONO_RESTAURANTE`
- ✅ Alteração de senha segura (com criptografia)
- ✅ Atualização de perfil de usuário
- ✅ Busca de usuários por nome
- ✅ Versionamento de API (`/api/v1/...`)
- ✅ Documentação interativa com Swagger/OpenAPI 3.0
- ✅ Banco de dados MySQL integrado
- ✅ Docker e Docker Compose para fácil deploy
- ✅ Validação robusta de dados
- ✅ Tratamento de exceções personalizado

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| **Java** | 21 | Linguagem principal |
| **Spring Boot** | 4.0.7 | Framework web |
| **Spring Data JPA** | 4.0.7 | ORM e acesso a dados |
| **Spring Validation** | 4.0.7 | Validação de dados |
| **MySQL** | 8.0 | Banco de dados |
| **Lombok** | - | Redução de boilerplate |
| **OpenAPI/Swagger** | 3.0.3 | Documentação de API |
| **Maven** | 3.9.6 | Gerenciador de dependências |
| **Docker** | Latest | Containerização |

## 📦 Requisitos

### Para execução com Docker
- Docker 20.10+
- Docker Compose 2.0+

### Para execução local
- Java 21 JDK (Eclipse Temurin recomendado)
- Maven 3.9.6+
- MySQL 8.0+

## 🚀 Instalação

### 1. Clonar repositório

```bash
git clone https://github.com/hersonmarinho/tech_challange_fiap_f1.git
cd hora-do-prato
```

### 2. Configurar variáveis de ambiente (opcional para local)

Criar arquivo `.env` ou configurar em `application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/meu_banco
    username: java_user
    password: javapassword
```

## ▶️ Como Executar

### Opção 1: Docker Compose (Recomendado)

```bash
# Build e iniciar containers
docker-compose up --build

# App estará disponível em http://localhost:8080
# MySQL estará disponível em localhost:3306
```

Credenciais do banco:
- **User:** `java_user`
- **Password:** `javapassword`
- **Database:** `meu_banco`

### Opção 2: Executar localmente

Pré-requisitos: MySQL rodando em `localhost:3306`

```bash
# Build do projeto
./mvnw clean package

# Executar aplicação
./mvnw spring-boot:run

# App estará disponível em http://localhost:8080
```

### Opção 3: Build Docker manual

```bash
# Build da imagem
docker build -t horadoprato:1.0 .

# Executar container
docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/meu_banco horadoprato:1.0
```

## 📁 Estrutura do Projeto

```
src/main/java/br/com/fiap/horadoprato/
├── controller/
│   └── UsuarioController.java         # Endpoints da API
├── services/
│   ├── UsuarioService.java            # Lógica de negócios
│   └── AuthService.java               # Autenticação
├── model/
│   ├── Usuario.java                   # Entidade usuário
│   └── Endereco.java                  # Entidade endereço
├── repositories/
│   └── UsuarioRepository.java         # Acesso a dados
├── dto/
│   ├── UsuarioRequestDTO.java
│   ├── UsuarioResponseDTO.java
│   ├── UsuarioUpdateDTO.java
│   └── SenhaUpdateDTO.java
├── infra/
│   ├── security/
│   │   └── CriptografiaUtil.java      # Criptografia de senhas
│   └── exception/
│       └── GlobalExceptionHandler.java # Tratamento global de erros
└── api/
    └── UsuariosApi.java               # Contrato gerado (OpenAPI)

src/main/resources/
├── api/
│   └── usuarios-api.yaml              # Definição OpenAPI
└── application.yaml                   # Configurações da aplicação
```

## 🔌 API Endpoints

### Base URL
```
http://localhost:8080/api/v1
```

### Autenticação
```http
POST /auth/login
Content-Type: application/json

{
  "login": "usuario",
  "senha": "senha123"
}
```

### Usuários

#### Cadastrar Usuário
```http
POST /usuarios/cadastrar
Content-Type: application/json

{
  "nome": "João Silva",
  "login": "joao_silva",
  "senha": "SenhaSegura@123",
  "email": "joao@horadoprato.com",
  "tipoUsuario": "CLIENTE",
  "endereco": {
    "logradouro": "Rua das Flores",
    "numero": "123",
    "complemento": "Apto 42",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01310-100"
  }
}
```

**Response (201):**
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "nome": "João Silva",
  "login": "joao_silva",
  "email": "joao@horadoprato.com"
}
```

#### Buscar Usuários por Nome
```http
GET /usuarios/buscar?nome=João
```

**Response (200):**
```json
[
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "nome": "João Silva",
    "login": "joao_silva",
    "email": "joao@horadoprato.com"
  }
]
```

#### Alterar Senha
```http
PATCH /usuarios/{id}/senha
Content-Type: application/json

{
  "senhaAntiga": "SenhaSegura@123",
  "novaSenha": "NovaSenha@456"
}
```

**Response (204 No Content)**

#### Atualizar Dados
```http
PATCH /usuarios/{id}/atualizar
Content-Type: application/json

{
  "nome": "João Silva Updated",
  "tipoUsuario": "CLIENTE",
  "endereco": {
    "logradouro": "Avenida Paulista",
    "numero": "1000",
    "complemento": null,
    "bairro": "Bela Vista",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01310-100"
  }
}
```

**Response (204 No Content)**

#### Deletar Usuário
```http
DELETE /usuarios/{id}
```

**Response (204 No Content)**

## 📮 Coleção Postman

Uma coleção completa Postman está incluída no projeto com 23 requisições cobrindo todos os cenários:

**Arquivo:** `postman-collection.json`

### Como importar

1. Abra Postman
2. Clique em **Import**
3. Selecione `postman-collection.json`
4. Na aba **Environment**, configure:
   - `base_url`: `http://localhost:8080` (padrão)
   - Outros campos serão preenchidos automaticamente pelos testes

### Cenários cobertos

**Cadastro** (6 testes)
- ✅ Cadastro válido - Cliente
- ✅ Cadastro válido - Dono Restaurante
- ❌ Email duplicado
- ❌ Login duplicado
- ❌ Campos obrigatórios faltando
- ❌ Tipo de usuário inválido (GARCOM)

**Senha** (4 testes)
- ✅ Alterar com sucesso
- ❌ Senha antiga incorreta
- ❌ Nova senha igual à anterior
- ❌ Usuário não encontrado

**Atualização** (3 testes)
- ✅ Atualizar com sucesso
- ❌ Usuário não encontrado
- ❌ Tipo de usuário inválido

**Busca** (3 testes)
- ✅ Buscar por nome completo
- ✅ Buscar por nome parcial
- ✅ Nenhum resultado

**Deleção** (2 testes)
- ✅ Deletar com sucesso
- ❌ Usuário não encontrado

## ⚙️ Configuração de Ambiente

### Variáveis de Ambiente (Docker)

No `docker-compose.yml`, configure:

```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/meu_banco
  SPRING_DATASOURCE_USERNAME: java_user
  SPRING_DATASOURCE_PASSWORD: javapassword
```

### Properties Locais

Editar `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/meu_banco
    username: java_user
    password: javapassword
  jpa:
    hibernate:
      ddl-auto: update
```

## 📖 Documentação

### Swagger UI

Após iniciar a aplicação, acesse:
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON
```
http://localhost:8080/v3/api-docs
```

## 🎯 Tipos de Usuário

| Tipo | Valor | Descrição |
|---|---|---|
| Cliente | `CLIENTE` | Usuário que faz reservas |
| Dono de Restaurante | `DONO_RESTAURANTE` | Proprietário do restaurante |

## ❌ Códigos de Erro

| Status | Descrição | Exemplo |
|---|---|---|
| 201 | Criado com sucesso | Cadastro bem-sucedido |
| 204 | Sem conteúdo (sucesso) | Atualização, deleção |
| 400 | Requisição inválida | Login duplicado, campo faltando |
| 404 | Não encontrado | Usuário inexistente |
| 500 | Erro interno | Erro no servidor |

## 🔐 Segurança

- Senhas armazenadas com **criptografia BCrypt**
- Validação de dados de entrada
- Tratamento robusto de exceções
- HTTPS recomendado em produção

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

**Herson Marinho**  
GitHub: [@hersonmarinho](https://github.com/hersonmarinho)

## 📞 Contato

Para dúvidas ou sugestões, abra uma [issue](https://github.com/hersonmarinho/tech_challange_fiap_f1/issues).

---

**Última atualização:** Agosto 2026
