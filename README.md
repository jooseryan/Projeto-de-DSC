# Projeto-de-DSC

# Sistema de Gerenciamento de Fontes de trabalhos acadêmicos

Este projeto foi desenvolvido como parte da disciplina **Desenvolvimento de Sistemas Corporativos** na Universidade Federal da Paraíba (UFPB).  
O objetivo é fornecer uma API RESTful segura para o gerenciamento de **usuários** e **fontes acadêmicas**, incluindo autenticação com **JWT**, documentação com **Swagger** e integração com banco de dados **H2** (para testes).

---

## 🚀 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Security (JWT)**
- **Spring Validation**
- **OpenAPI (Swagger)**
- **H2**
- **Maven**

---

## ⚙️ Como Executar o Projeto

O sistema estará disponível em:

http://localhost:8080

📑 Documentação da API

A documentação está disponível via Swagger UI em:

http://localhost:8080/swagger-ui/index.html

🔑 Autenticação

O sistema usa JWT. 

Registrar um usuário

POST /user/register
Content-Type: application/json

{
  "username": "admin",
  "password": "12345678",
  "role": ["ROLE_ADMIN"]
}


Fazer login

POST /user/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}


Resposta:

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}


Usar o token
Inclua no cabeçalho das requisições:

Authorization: Bearer SEU_TOKEN

📂 Endpoints Principais

## 👤 Usuários

POST /user/register → Criar usuário

POST /user/login → Autenticar usuário e gerar token

GET /user → Listar todos os usuários

GET /user/{id} → Buscar usuário por ID

PUT /user/{id} → Atualizar usuário

PATCH /user/{id} → Atualização parcial

DELETE /user/{id} → Excluir usuário

GET /user/search?username=nome&role=ROLE_USER → Buscar usuários com filtros

## 📚 Fontes Acadêmicas

POST /library/add → Criar fonte

GET /library → Listar fontes (paginado)

GET /library/{id} → Buscar por ID

PUT /library/{id} → Atualizar fonte

PATCH /library/{id} → Atualização parcial

DELETE /library/{id} → Excluir

GET /library/search?title=...&author=...&yearStart=2020&yearEnd=2023&type=ARTIGO → Buscar com filtros

