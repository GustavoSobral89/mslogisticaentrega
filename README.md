# Microserviço de Logística de Entregas

Este microserviço de logística é responsável por gerenciar o processo de entregas, associando pedidos a entregadores, e controlando o status das entregas.

## Funcionalidades

1. **Criação de Entregador**: Permite criar um novo entregador com status "Disponível" por padrão.
2. **Listagem de Entregadores**: Exibe todos os entregadores cadastrados.
3. **Busca de Entregador por ID**: Permite buscar um entregador específico pelo ID.
4. **Criação de Entrega**: Cria uma nova entrega associando um pedido a um entregador, e define o status de "Aguardando Atribuição".
5. **Iniciar Entrega**: Atualiza o status da entrega para "Em Trânsito" e o status do entregador para "Em Rota".
6. **Finalizar Entrega**: Atualiza o status da entrega para "Entregue" e o status do entregador para "Indisponível".

## Endpoints

### 1. **Entregadores**

#### Criar um novo entregador

- **Método**: `POST`
- **Endpoint**: `/entregadores`
- **Descrição**: Cria um novo entregador com status "Disponível".
- **Resposta**:
  - `201`: Entregador criado com sucesso.
  - `400`: Dados inválidos.

#### Listar todos os entregadores

- **Método**: `GET`
- **Endpoint**: `/entregadores`
- **Descrição**: Retorna uma lista de todos os entregadores cadastrados.
- **Resposta**:
  - `200`: Lista de entregadores retornada com sucesso.
  - `500`: Erro ao recuperar entregadores.

#### Buscar entregador por ID

- **Método**: `GET`
- **Endpoint**: `/entregadores/{id}`
- **Descrição**: Retorna um entregador com base no ID.
- **Resposta**:
  - `200`: Entregador encontrado.
  - `404`: Entregador não encontrado.

---

### 2. **Logística**

#### Criar uma nova entrega

- **Método**: `POST`
- **Endpoint**: `/logistica/entrega`
- **Descrição**: Cria uma nova entrega associando um pedido a um entregador.
- **Resposta**:
  - `201`: Entrega criada com sucesso.
  - `400`: Erro ao criar entrega, entregador indisponível ou dados inválidos.

#### Iniciar uma entrega

- **Método**: `PUT`
- **Endpoint**: `/logistica/entrega/iniciar/{entregaId}`
- **Descrição**: Atualiza o status da entrega para "Em Trânsito" e o status do entregador para "Em Rota".
- **Resposta**:
  - `200`: Entrega iniciada com sucesso.
  - `404`: Entrega não encontrada.

#### Finalizar uma entrega

- **Método**: `PUT`
- **Endpoint**: `/logistica/entrega/finalizar/{entregaId}`
- **Descrição**: Atualiza o status da entrega para "Entregue" e o status do entregador para "Indisponível".
- **Resposta**:
  - `200`: Entrega finalizada com sucesso.
  - `404`: Entrega não encontrada.

## Estrutura do Projeto

### **Entidades**

- **Entrega**: Representa uma entrega, associando um pedido, entregador, e status da entrega.
- **Entregador**: Representa um entregador, com seu nome, veículo e status.
- **PedidoDTO**: Representa os dados do pedido, incluindo ID, cliente, itens e status.
- **ItemPedidoDTO**: Representa os itens de um pedido com ID, quantidade e preço.

### **Enumerações**

- **StatusEntregadorEnum**: Enum para os status dos entregadores (Disponível, Alocado, Em Rota, Indisponível).
- **StatusEntregaEnum**: Enum para os status das entregas (Aguardando Atribuição, Criada, Em Trânsito, Entregue, Cancelada).

## Configuração do Banco de Dados

A configuração do banco de dados está realizada para o PostgreSQL, com as seguintes propriedades:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fase4
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.platform=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

Como Rodar o Projeto

Clone o repositório.

Configure o banco de dados PostgreSQL conforme as propriedades acima.
Execute a aplicação com o comando:
bash
```
mvn spring-boot:run
A aplicação estará disponível na URL: http://localhost:8083.
```

Dependências
Spring Boot
Spring Data JPA
PostgreSQL
OpenAPI (Swagger)