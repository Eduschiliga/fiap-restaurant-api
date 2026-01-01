# FIAP Restaurant API - Gerenciamento de Usuários (Fase 1)

[![Java](https://img.shields.io/badge/Java-25%20(Preview)-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.0-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Arquitetura](https://img.shields.io/badge/Arquitetura-Hexagonal-blue?style=for-the-badge)](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))

## 📝 Índice

1. [Introdução](#1-introdução)
2. [Arquitetura do Sistema](#2-arquitetura-do-sistema)
3. [Estrutura do Banco de Dados](#3-estrutura-do-banco-de-dados)
4. [API Endpoints](#4-api-endpoints)
5. [Configuração e Execução Local](#5-configuração-e-execução-local)
6. [Qualidade do Código e Testes](#6-qualidade-do-código-e-testes)
7. [Exemplos de Uso (cURL)](#7-exemplos-de-uso-curl)

---

## 1. Introdução

Este projeto é a primeira fase do **Tech Challenge** da disciplina de Arquitetura e Desenvolvimento em Java, focado no desenvolvimento de um backend robusto para um sistema unificado de gestão de restaurantes.

O objetivo desta fase é desenvolver um backend utilizando **Spring Boot** para gerenciar o cadastro de usuários (Clientes e Donos de Restaurante), garantindo a segurança, escalabilidade e a correta persistência dos dados. A infraestrutura é totalmente provisionada via Docker.

| Tópico | Detalhes |
| :--- | :--- |
| **Projeto** | FIAP Restaurant API |
| **Fase** | 1 - Gestão de Usuários e Autenticação |
| **Tecnologia Principal** | Spring Boot (Java 25) |
| **Persistência** | PostgreSQL 17.0 |
| **Segurança** | JSON Web Tokens (JWT) e BCrypt |

## 2. Arquitetura do Sistema

O sistema foi desenvolvido sob o paradigma da **Arquitetura Hexagonal** (Ports and Adapters), visando o desacoplamento estrito entre a lógica de negócio central (Application Core) e as tecnologias de infraestrutura (banco de dados, frameworks, APIs externas).

Este modelo promove a **testabilidade**, a **manutenibilidade** e a **portabilidade** do sistema, alinhando-se aos princípios de SOLID e Clean Architecture.

### Core da Aplicação (O Hexágono)

O coração da aplicação é o **Application Core**, independente de frameworks.

| Camada | Componentes Chave | Função |
| :--- | :--- | :--- |
| **Domain** | `User`, `Address`, `UserType` | Contém as Entidades de Domínio e as Regras de Negócio mais críticas. |
| **Ports** | `Inbound Ports` (Ex: `CreateUserPort`), `Outbound Ports` (Ex: `UserRepositoryPort`) | Interfaces Java que definem os contratos de comunicação entre o Core e o mundo externo (infraestrutura). |
| **Use Cases** | `CreateUserUseCase`, `AuthenticateUserUseCase` | Implementam as Portas de Entrada, orquestrando o fluxo de dados e invocando as regras de domínio. |

### Adaptadores (A Infraestrutura)

Os adaptadores residem fora do Core e traduzem as chamadas entre o mundo externo e as Portas do Core.

| Tipo de Adaptador | Componentes Chave | Tecnologia | Função |
| :--- | :--- | :--- | :--- |
| **Driving (Entrada)** | `UserController`, `AuthController` | Spring Boot REST | Recebem requisições HTTP e as traduzem para chamadas de método nas Portas de Entrada. |
| **Driven (Saída)** | `UserRepositoryPortAdapter`, `BCryptPasswordEncoderAdapter`, `JwtTokenAdapter` | Spring Data JPA, BCrypt, JWT | Implementam as Portas de Saída, adaptando a chamada do Core para a tecnologia específica (persistência, criptografia, etc.). |

## 3. Estrutura do Banco de Dados

O sistema utiliza um banco de dados relacional **PostgreSQL (v17.0)**, gerenciado pelo Hibernate (JPA).

### Entidades Principais

*   **Users:** Representa os atores do sistema (Clientes e Donos de Restaurante). Armazena credenciais de acesso e informações de auditoria.
*   **Addresses:** Representa os dados de localização física, modelada separadamente para normalização.

### Relacionamento

O sistema implementa um relacionamento **1:1 (Um-para-Um)** unidirecional entre `User` e `Address`. Cada usuário possui um único endereço principal, referenciado pela chave estrangeira `address_id` na tabela `users`.

### Esquema da Tabela `users`

| Coluna | Tipo (SQL) | Restrições | Descrição |
| :--- | :--- | :--- | :--- |
| `id` | `VARCHAR(36)` | PK, Not Null, Unique | Identificador único (UUID). |
| `name` | `VARCHAR(255)` | Not Null | Nome completo do utilizador. |
| `email` | `VARCHAR(255)` | UK (Unique), Not Null | E-mail para contacto e chave única. |
| `login` | `VARCHAR(255)` | UK (Unique), Not Null | Identificador utilizado no login. |
| `password` | `VARCHAR(255)` | Not Null | Hash da senha do utilizador. |
| `user_type` | `SMALLINT` | Not Null | Enum: `0` (CLIENT), `1` (RESTAURANT_OWNER). |
| `address_id` | `VARCHAR(36)` | FK (Ref. addresses) | Chave estrangeira para o endereço. |
| `created_at` | `TIMESTAMP` | Not Null | Data de criação do registo. |
| `updated_at` | `TIMESTAMP` | Not Null | Data da última atualização. |

## 4. API Endpoints

A API utiliza a estratégia de **Versionamento via URI** (`/api/{versao}/{recurso}`) para garantir a evolução controlada dos contratos de interface. A versão atual é **v1**.

### Tabela de Endpoints (v1)

| Endpoint | Método | Descrição |
| :--- | :--- | :--- |
| `/api/v1/auth/login` | `POST` | Realiza a autenticação do usuário, retornando um JWT. |
| `/api/v1/users` | `POST` | Cria um novo usuário (Cliente ou Dono). |
| `/api/v1/users` | `GET` | Lista todos os usuários (com paginação). |
| `/api/v1/users/{userId}` | `PUT` | Atualiza os dados cadastrais do usuário. |
| `/api/v1/users/{userId}` | `GET` | Busca usuário por ID. |
| `/api/v1/users/{userId}` | `DELETE` | Deleta os dados de um usuário. |
| `/api/v1/users/search` | `GET` | Busca usuários por nome (com paginação). |
| `/api/v1/users/{userId}/password` | `PATCH` | Realiza a atualização da senha do usuário. |

### Documentação Interativa (Swagger)

A documentação interativa da API está disponível no seguinte endereço após a execução local:

> `http://localhost:8080/swagger-ui/index.html#/`

## 5. Configuração e Execução Local

O ambiente de desenvolvimento é totalmente conteinerizado, garantindo a reprodutibilidade e consistência.

### Pré-requisitos

É necessário ter o **Docker Engine** e o **Docker Compose** instalados e em execução na máquina host.

*   [Docker Desktop](https://www.docker.com/products/docker-desktop) (Recomendado para Windows/Mac/Linux)

### Conteinerização (Docker)

A aplicação utiliza uma estratégia de **Multi-stage Build** no `Dockerfile` para otimizar o tamanho da imagem final, baseada em `eclipse-temurin:25-jdk-alpine` (build) e `eclipse-temurin:25-jre-alpine` (runtime).

### Orquestração (Docker Compose)

O arquivo `docker-compose.yml` orquestra dois serviços em uma rede privada (`fiap-network`):

1.  **`postgres`**: Utiliza a imagem oficial `postgres:17.0`. Configurado com `restart: always` e um `Healthcheck` robusto (`pg_isready`) para garantir que o banco esteja pronto antes da aplicação.
2.  **`fiap-user-service`**: A aplicação Spring Boot. Utiliza `depends_on` com `condition: service_healthy` para aguardar o banco de dados, eliminando erros de "Connection Refused".

### Passos para Execução

Na raiz do projeto (onde se encontra o arquivo `docker-compose.yml`), execute o comando abaixo para construir as imagens localmente e iniciar os containers em segundo plano:

```bash
docker-compose up -d --build
```

### Acessos Pós-Execução

| Serviço | Endereço | Credenciais |
| :--- | :--- | :--- |
| **API (Swagger)** | `http://localhost:8080/swagger-ui/index.html#/` | N/A |
| **Banco de Dados** | `localhost:5432` | **User:** `root` / **Password:** `root` / **Database:** `restaurantdb` |

## 6. Qualidade do Código e Testes

### Boas Práticas de Código

*   **Arquitetura Hexagonal:** Separação clara entre Domínio, Aplicação e Infraestrutura.
*   **SOLID:** Aplicação dos princípios, como Inversão de Dependência (Use Cases dependem de interfaces, não de implementações JPA).
*   **DTOs (Java Records):** Uso de `LoginInput`, `CreateUserOutput` para transferência de dados imutáveis, evitando expor as entidades de domínio.
*   **Tratamento de Exceções:** Uso de `@ControllerAdvice` (`GlobalExceptionHandler`) para padronizar respostas de erro seguindo o padrão **Problem Detail (RFC 7807)**.
*   **Segurança:** Implementação de `SecurityFilter` para validação de tokens JWT e criptografia de senhas com BCrypt.

### Testes Automatizados

A estratégia prioriza **Testes Unitários** focados na camada de **Aplicação (Use Cases)**, garantindo que a lógica central funcione independentemente de dependências externas.

| Tecnologia | Função |
| :--- | :--- |
| **JUnit 5** | Framework principal para estruturação e execução dos testes. |
| **Mockito** | Utilizado para criar mocks das Portas de Saída (Repositórios, Encoders), isolando os Use Cases de dependências de infraestrutura (como o banco de dados). |

Os testes validam o "caminho feliz" e os fluxos de exceção para as seguintes funcionalidades:

*   **Gestão de Usuários:** Criação (criptografia de senha), Busca (por ID, nome, paginação), Atualização e Exclusão.
*   **Autenticação:** Sucesso no login, falha por credenciais inválidas, e geração do token JWT.
*   **Gestão de Senhas:** Validação da senha antiga, regras de negócio para a nova senha e criptografia.

## 7. Exemplos de Uso (cURL)

Os exemplos abaixo utilizam a URL base `http://localhost:8080`.

### 1. Criar um Novo Usuário (`POST /api/v1/users`)

```bash
curl --location 'http://localhost:8080/api/v1/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Eduardo Schiliga",
    "email": "dev.edu@gmail.com",
    "login": "dev.edu",
    "password": "&5u4rd0-D37",
    "address": {
        "street": "Avenida Silva Jardim",
        "number": "1450",
        "complement": "Bloco B - Apto 402",
        "city": "Curitiba",
        "state": "PR",
        "zipCode": "80230-000"
    },
    "userType": "CLIENT"
}'
```

### 2. Autenticar Usuário (`POST /api/v1/auth/login`)

```bash
curl --location 'http://localhost:8080/api/v1/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "login": "dev.edu",
    "password": "&5u4rd0-D37"
}'
# Retorna um JWT no corpo da resposta.
```

### 3. Atualizar Senha (`PATCH /api/v1/users/{userId}/password`)

**Nota:** O token JWT deve ser obtido no login e incluído no cabeçalho `Authorization: Bearer <TOKEN>`.

```bash
curl --location --request PATCH 'http://localhost:8080/api/v1/users/bac885c7-9e7b-4cd1-97b7-270c14907679/password' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN_JWT_AQUI>' \
--data '{
    "newPassword": "eduardo.dev",
    "oldPassword": "&5u4rd0-D37"
}'
```

### 4. Buscar Usuários por Nome (`GET /api/v1/users/search`)

```bash
curl --location 'http://localhost:8080/api/v1/users/search?name=edu&page=0&perPage=10' \
--header 'Authorization: Bearer <TOKEN_JWT_AQUI>'
```

### 5. Deletar Usuário (`DELETE /api/v1/users/{userId}`)

```bash
curl --location --request DELETE 'http://localhost:8080/api/v1/users/1a99b158-edc2-43a0-a2c3-f9e894737dc4' \
--header 'Authorization: Bearer <TOKEN_JWT_AQUI>'
```
