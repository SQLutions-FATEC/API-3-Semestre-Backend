# API-3-Semestre-Backend

# 🚀 Tecnologias usadas

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://swagger.io/)

## 🧱 Estrutura do Projeto

O projeto segue uma estrutura de pastas agrupadas por tipo, onde os arquivos são organizados com base em sua função ou recurso no sistema. Essa abordagem facilita a manutenção e a localização de arquivos relacionados a uma mesma camada ou funcionalidade.

Aqui está uma visão geral da estrutura:

```text
src
└── main
    ├── java
    │   └── com.sqlutions.altave
    │       ├── config
    │       ├── controller
    │       ├── dto
    │       ├── entity
    │       ├── exception
    │       ├── repository
    │       ├── service
    │       │   └── impl
    │       └──── Api3SemestreBackendApplication.java                         
    └── resources
        ├── config.checkstyle
        │   └── google_checks.xml
        ├── photos
        ├── application.properties
        └── data.sql
```

## 📦 Ferramentas Necessárias

Para o desenvolvimento do backend, você vai precisar das seguintes ferramentas:

- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Java 21 (Zulu - Azul Zulu Community)](https://www.azul.com/downloads/)
- [Docker](https://www.docker.com/)
- [Git](https://git-scm.com/)

---

## 🔽 Clonando o Repositório

1. Abra o IntelliJ IDEA.
2. Clique em **"Get from Version Control"** ou **"Clone repository"**.
3. Insira a URL do repositório:
   https://github.com/SQLutions-FATEC/API-3-Semestre-Backend.git

4. Clique em **Clone**.

## ☕ Configurando o Java 21 (Zulu)

1. Vá até `File > Project Structure`.
2. Em **Project SDK**, verifique se o **Java 21** (preferencialmente **Zulu 21.0.6**) está configurado.
3. Caso não esteja:
- Clique em **Add SDK > Download JDK**.
- Selecione:
    - **Version**: 21
    - **Vendor**: Azul Zulu Community
- Clique em **Download**.
4. Após o download, selecione o SDK e clique em **Apply**.

---

## 🐳 Executando o Docker Compose

Este projeto utiliza um banco de dados PostgreSQL configurado com Docker, facilitando a padronização do ambiente entre os desenvolvedores.

O serviço é orquestrado através do arquivo `compose.yaml`, que se encontra na raiz do projeto.

✅ **Etapas para execução**

1. Instale o Docker, caso ainda não tenha:  
   Siga a [documentação oficial](https://docs.docker.com/get-docker/).

2. Certifique-se de que o Docker está em execução na sua máquina.

3. No terminal, navegue até a raiz do projeto onde está localizado o arquivo `compose.yaml` e execute o comando abaixo:

```bash
docker compose up -d
```
Esse comando fará com que o Docker:

- Baixe a imagem do PostgreSQL (caso ainda não tenha).
- Crie e execute um container com as configurações definidas no `compose.yaml`.
- Exponha a porta padrão (5432) para conexão com a aplicação.

🔎 **Verificando se o container está em execução**

Execute:

```bash
docker ps
```

---

## ⚙️ Executando o Projeto Spring Boot

1. No IntelliJ, abra a classe principal da aplicação:

   src/main/java/com/sqlutions/altave/Api3SemestreBackendApplication.java

2. Clique no botão verde de **Run** ou use o atalho **Shift + F10** para iniciar a aplicação.

3. Aguarde até que a aplicação inicialize completamente. Você verá no console uma mensagem indicando que o servidor está rodando na porta padrão (geralmente `8080`).

---

### 📄 Documentação da API

A API conta com uma interface interativa gerada pelo Swagger, que permite visualizar e testar os endpoints disponíveis diretamente pelo navegador.

Após iniciar o projeto Spring Boot, acesse a URL abaixo:

🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

> ℹ️ Caso a página não carregue, verifique se a aplicação está rodando corretamente no IntelliJ e se não há erros no console.

---

# Padrão de commits/branch

Seguiremos o Conventional Commits porque ele padroniza as mensagens, facilitando a leitura do histórico e a integração com ferramentas.

## Commits

`<tipo>: <mensagem em português>`

Exemplo:<br>
feat: adiciona métodos à interface PhotoService para manipulação de fotos

## Branch

`feat(<ID da tarefa no Jira>):<breve descrição do que está sendo implementado>`

Exemplo:<br>
feat(SCRUM-68): implementa padronização dos DTOs com atributos em inglês






