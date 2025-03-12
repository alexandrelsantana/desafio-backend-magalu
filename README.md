
## 📌 Índice
1. [Objetivo](#objetivo)
2. [O Desafio](#desafio)
3. [Decisões técnicas](#decisões)
4. [Como Executar](#como-executar)
5. [API documentations](#api-doc)
    - [POST /api/create-scheduler](#create-scheduler)
    - [POST /api/cancel-scheduler](#cancel-scheduler)
    - [GET /api/retrieve-scheduler](#retrieve-scheduler)



# Projeto desafio-backend-magalu
## 📌 Objetivo  <a id="objetivo"></a>
<h4>
Esse projeto tem por objetivo resolver a um suposto desafio de processo seletivo para desenvolvedor da empresa Magalu.
Encontrei pela internet a vários desenvolvedores resolvendo esse desafio, mas sempre utilizando MVC e Spring Scheduler.
Como não identifiquei ninguém que tenha resolvido com arquitetura hexagonal e DDD, optei por usar esse padrão de design para resolvê-lo.
Ainda faltam alguns ajustes em testes na camada de infraestrutura e reagendar os schedulers quando a aplicação é 'reestartada', mas em breve os farei.
</h4>

## 📌 O DESAFIO  <a id="desafio"></a>
## desafio-backend-magalu

**Bem-vindo(a) ao nosso processo seletivo**

**Cenário**

O Magalu tem o desafio de desenvolver uma plataforma de comunicação. Você foi escolhido(a) para iniciar o desenvolvimento da primeira sprint.

**Requisitos**

- **Deve ter um endpoint que receba uma solicitação de agendamento de envio de comunicação (1):**
    - Este endpoint precisa ter no mínimo os seguintes campos:
        - Data/Hora para o envio
        - Destinatário
        - Mensagem a ser entregue
    - As possíveis comunicações que podem ser enviadas são: email, SMS, push e WhatsApp.
    - Neste momento, precisamos deste canal de entrada para realizar o envio, ou seja, esse endpoint (1). O envio em si não será desenvolvido nesta etapa: você não precisa se preocupar com isso.
    - Para esta sprint ficou decidido que a solicitação do agendamento do envio da comunicação será salva no banco de dados. Portanto, assim que receber a solicitação do agendamento do envio (1), ela deverá ser salva no banco de dados.
    - Pense com carinho nessa estrutura do banco. Apesar de não ser você quem vai realizar o envio, a estrutura já precisa estar pronta para que o seu coleguinha não precise alterar nada quando for desenvolver esta funcionalidade. A preocupação no momento do envio será de enviar e alterar o status do registro no banco de dados.

- **Deve ter um endpoint para consultar o status do agendamento de envio de comunicação (2):**
    - O agendamento será feito no endpoint (1) e a consulta será feita por este outro endpoint.

- **Deve ter um endpoint para remover um agendamento de envio de comunicação.**

**Observações e Orientações Gerais**

- Temos preferência por desenvolvimento na linguagem Java, Python ou Node, mas pode ser usada qualquer linguagem; depois, apenas nos explique o porquê da sua escolha.
- Utilize um dos bancos de dados abaixo:
    - MySQL
    - PostgreSQL
- As APIs deverão seguir o modelo RESTful com formato JSON.
- Faça testes unitários, foque em uma suíte de testes bem organizada.
- Siga o que considera como boas práticas de programação.
- A criação do banco e das tabelas fica a seu critério de como será feita, seja via script, aplicação, etc.

Seu desafio deve ser enviado preferencialmente como repositório GIT público (Github, Gitlab, Bitbucket), com commits pequenos e bem descritos, ou como arquivo compactado (ZIP ou TAR). O seu repositório deve estar com um modelo de licença de código aberto. Não envie nenhum arquivo além do próprio código compactado e sua documentação. Tome cuidado para não enviar imagens, vídeos, áudio, binários, etc.

Siga boas práticas de desenvolvimento, de qualidade e de governança de código. Oriente os avaliadores a como instalar, testar e executar seu código: pode ser um README dentro do projeto.

Iremos avaliar seu desafio de acordo com a posição e o nível que você está se candidatando.

Agradecemos muito sua disposição de participar do nosso processo seletivo e desejamos que você se divirta e que tenha boa sorte :)


## 📌 Decisões técnicas <a id="decisões"></a>
<h4>
- Para que a aplicação possa ser escalada horizontalmente, antes de todo o envio de mensagem, há uma validação no banco
de dados se a mensagem continua com status diferente de cancelado.<br>
- Não há como saber em qual dos n serviços sendo executados está agendada o envio da mensagem, dessa forma, caso seja
necessário cancelar seu envio, basta alterar o status no banco de dados para cancelado<br>
- Poderia ter sido utilizado um spring scheduler, mas isso obrigaria executar o serviço de minuto a minuto, o que achei
pouco elegante para a solução, principalmente considerando ser um exercício.<br>
</h4>
<h4>
Em relação a tecnologias, optou-se usar:<br>
- Java 17;<br>
- Spring Boot framework;<br>
- PostgreSQL<br>
- Docker para desenvolvimento.<br>
</h4>

### 📌 Como executar: <a id="como-executar"></a>
<h4>
Para executar via docker, foi desenvolvido o Dockerfile docker-compose, que tem o objetivo de criar o container de banco de
dados e do serviço Spring.
</h4>
<h4>
Inicialmente é necessário criar e configurar o arquivo application.properties conforme modelo disponibilizado
 em INFRASTRUCTURE/src/main/resources/application.properties.model.<br>
Para configurá-lo com o banco disponibilizado, adicionar as seguintes configurações em INFRASTRUCTURE/src/main/resources/:<br>
</h4>

```
# application.properties
spring.datasource.url=jdbc:postgresql://scheduler_db:5432/db
spring.datasource.username=postgres
spring.datasource.password=12345
```

<h4>
E subir os containers com o comando abaixo:
</h4>

```
./execute.sh
```

# 📖 API Documentation <a id="api-doc"></a>

## 📌 Base URL
```
http://localhost:9092
```

---

### 🚀 Endpoints <a id="endpoints"></a>
# OpenAPI Definition

## Info

- **Title:** OpenAPI Definition
- **Version:** v0

## Servers

- **URL:** `http://localhost:9092`
- **Description:** Generated server URL

## Endpoints

### **Schedule a Message** <a id="create-scheduler"></a>

**POST** `/api/create-scheduler`

- **Description:** This endpoint schedules a message to be sent at a specific time.
- **Request Body:**
  ```json
  {
    "message": "Message",
    "to": "teste@domain.com",
    "scheduled_time": "01/01/2999 00:00"
  }
  ```
- **Responses:**
    - **200 OK** (Message scheduled successfully)
      ```json
      {
        "isSuccess": true,
        "message": "Message scheduled",
        "data": {
          "uuid": "48b02c3d4c154806a7748a05ac72de3f",
          "scheduledTime": "2999-01-01T00:00:00",
          "message": { "message": "teste message", "to": "alexandre@gmail.com" },
          "statusScheduler": { "status": "SCHEDULED" }
        },
        "statusCode": 200,
        "timestamp": 1741272361672,
        "path": "/api/create-scheduler",
        "errors": []
      }
      ```
    - **400 Bad Request** (Message not scheduled)
      ```json
      {
        "isSuccess": false,
        "message": "Message not scheduled",
        "data": {
          "uuid": null,
          "scheduledTime": "2999-01-01T00:00:00",
          "message": { "message": null, "to": null },
          "statusScheduler": { "status": "FAILED" }
        },
        "statusCode": 400,
        "timestamp": 1741262374033,
        "path": "/api/create-scheduler",
        "errors": [
          { "description": "Field 'to' is empty", "message": "The 'to' field is required." },
          { "description": "Field 'message' is empty", "message": "The 'message' field is required." }
        ]
      }
      ```

---

### **Retrieve Schedule Message** <a id="retrieve-scheduler"></a>

**GET** `/api/retrieve-scheduler`

- **Description:** This endpoint retrieves a scheduled message.
- **Query Parameters:**
    - `uuid` (string, required): UUID of the scheduled message.

- **Example Request:**
  ```http
  GET /api/retrieve-scheduler?uuid=70efeaf62baf45c1b28b9af8fe354223
  ```
- **Responses:**
    - **200 OK** (Scheduled Message retrieved successfully)
      ```json
      {
        "isSuccess": true,
        "message": "Message retrieve",
        "data": {
          "uuid": "e7ce10fd72c04ebb9dca1397daae4823",
          "scheduledTime": "2026-03-10T23:59:00",
          "message": { "message": "teste message5", "to": "alexandre@gmail.com" },
          "statusScheduler": { "status": "SCHEDULED" }
        },
        "statusCode": 200,
        "timestamp": 1741787143019,
        "path": "/api/retrieve-scheduler",
        "errors": []
      }
      ```
    - **400 Bad Request** (Message not retrieved)
      ```json
      {
        "isSuccess": false,
        "message": "Message not retrieve",
        "data": null,
        "statusCode": 400,
        "timestamp": 1741787679686,
        "path": "/api/retrieve-scheduler",
        "errors": [{ "description": "Error when validate uuid", "message": "uuid cannot be null or empty" }]
      }
      ```

---

### **Cancel Scheduled Message** <a id="cancel-scheduler"></a>

**POST** `/api/cancel-scheduler`

- **Description:** This endpoint cancels a scheduled message.
- **Request Body:**
  ```json
  {
    "uuid": "70efeaf62baf45c1b28b9af8fe354223"
  }
  ```
- **Responses:**
    - **200 OK** (Message canceled successfully)
      ```json
      {
        "isSuccess": true,
        "message": "Message Cancelled",
        "data": {
          "uuid": "d73e5317bb3d4345925684e899ab98b5",
          "scheduledTime": "2026-03-10T23:59:00",
          "message": { "message": "teste message", "to": "alexandre@gmail.com" },
          "statusScheduler": { "status": "CANCELLED" }
        },
        "statusCode": 200,
        "timestamp": 1741666614356,
        "path": "/api/cancel-scheduler",
        "errors": []
      }
      ```
    - **400 Bad Request** (Message not canceled)
      ```json
      {
        "isSuccess": false,
        "message": "Message not Cancelled",
        "data": {
          "uuid": "128b7e92b1fd49af9c928749a3ae565e",
          "scheduledTime": "2026-03-10T23:59:00",
          "message": { "message": "teste message", "to": "aaaaa@gmail.com" },
          "statusScheduler": { "status": "CANCELLED" }
        },
        "statusCode": 400,
        "timestamp": 1741666519205,
        "path": "/api/cancel-scheduler",
        "errors": [{ "description": "Error when cancel scheduled", "message": "Scheduled message is already cancelled" }]
      }
      ```

---
