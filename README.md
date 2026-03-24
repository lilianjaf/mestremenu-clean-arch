# Mestre Menu - Ecossistema de Gestão Compartilhada Arquitetura Limpa

## Descrição
O **Mestre Menu** é um ecossistema robusto para a gestão compartilhada de cardápios e estabelecimentos gastronômicos. O projeto foi desenvolvido com foco na modularidade, testabilidade e separação de preocupações, utilizando os princípios da **Clean Architecture**. Ele permite a gestão completa de restaurantes, usuários (com diferentes níveis de acesso) e seus respectivos cardápios.

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3.4.1**
- **Spring Data JPA** (Persistência)
- **Spring Security** & **JWT** (Segurança e Autenticação)
- **PostgreSQL** (Banco de dados relacional)
- **Flyway** (Migração de banco de dados)
- **Docker** & **Docker Compose** (Containerização)
- **JUnit 5** & **Mockito** (Testes unitários e integrados)
- **Gradle** (Gerenciamento de dependências e build)

## Arquitetura do Sistema
O projeto segue os princípios da **Clean Architecture**, organizado para garantir que o núcleo de negócio seja independente de frameworks, UI ou banco de dados.

- **Core**: Contém as regras de negócio centrais.
    - `domain`: Entidades de domínio.
    - `usecase`: Implementação dos casos de uso da aplicação.
    - `gateway`: Interfaces para comunicação com o mundo externo (infra).
    - `rules`: Regras de validação de negócio específicas.
    - `dto`: Objetos de transferência de dados.
- **Infra**: Contém as implementações técnicas.
    - `controller`: Adaptadores de entrada (REST API).
    - `gateway`: Implementações das interfaces do Core (JPA Repositories, Mappers).
    - `config`: Configurações de frameworks (Spring, Security, Beans).
- **Shared**: Spring Security.

## Instruções de Execução via Docker
Para executar o projeto utilizando o Docker, certifique-se de ter o Docker e o Docker Compose instalados.

1.  **Configurar Variáveis de Ambiente**:
    Certifique-se de que as variáveis de ambiente necessárias (como as do banco de dados no `docker-compose.yml`) estejam configuradas ou utilize um arquivo `.env` na pasta `compose`.

2.  **Executar o Compose**:
    Navegue até a raiz do projeto e execute:
    ```bash
    docker-compose -f compose/docker-compose.yml up --build
    ```

3.  **Acesso**:
    - A API estará disponível em `http://localhost:8080`
    - O banco de dados PostgreSQL estará na porta `5432`

## Documentação da API - Coleção do Postman

- **Postman**: Existe um arquivo de coleção pronto para importação na raiz do projeto:
    - `postmanCollection.json`

## Funcionalidades Implementadas

### Gestão de Restaurantes
- Cadastro de novos restaurantes.
- Atualização de informações cadastrais e endereços.
- Listagem e busca de restaurantes.

### Gestão de Cardápios
- Criação e edição de cardápios vinculados a restaurantes.
- Gestão de itens de cardápio (nome, descrição, preço, disponibilidade, foto).
- Exclusão de cardápios e itens.

### Gestão de Usuários e Segurança
- Cadastro de usuários e tipos de usuários (roles).
- Autenticação via JWT (JSON Web Token).
- Controle de acesso baseado em tipo de usuário.

## Testes
O projeto possui uma cobertura abrangente de testes automatizados para garantir a qualidade e a confiabilidade das regras de negócio.

- **Testes Unitários**: Focados nos `usecases` e `domain` rules, utilizando Mockito para isolar dependências.
- **Execução dos Testes**:
    Para rodar todos os testes do projeto, utilize o comando:
    ```bash
    ./gradlew test
    ```
- **Relatórios**: Os resultados dos testes podem ser encontrados em `build/reports/tests/test/index.html` após a execução.
