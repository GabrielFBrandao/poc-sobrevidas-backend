# Pacientes API

API REST para gerenciamento de pacientes, desenvolvida com Java 17 + Spring Boot + PostgreSQL + Keycloak.

## Tecnologias
- Java 17
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL (via Docker)
- Keycloak 24 (via Docker)
- Spring Security + OAuth2 + JWT
- Swagger / OpenAPI 3
- JUnit 5 + Mockito
- Testcontainers
- Lombok
- OpenCSV

## Arquitetura
```
src/main/java/br/com/sobrevidas/pacientesapi/
├── config/        → SecurityConfig, SwaggerConfig
├── controller/    → PacienteController (endpoints HTTP)
├── mapper/        → PacienteMapper (conversão DTO ↔ entidade)
├── model/         → Paciente (entidade JPA)
├── repository/    → PacienteRepository (acesso ao banco)
├── request/       → PacientePostRequestBody, PacientePutRequestBody
└── service/       → PacienteService, CsvImportService
```

## Como rodar

### Pré-requisitos
- Java 17+
- Docker Desktop

### 1. Clone o repositório
```bash
git clone https://github.com/GabrielFBrandao/poc-sobrevidas-backend.git
cd poc-sobrevidas-backend
```

### 2. Suba o banco de dados e o Keycloak
```bash
docker-compose up -d
```

### 3. Configure o Keycloak
Acesse http://localhost:8180 com usuário `admin` e senha `admin123`.

**Criar o Realm:**
1. Dropdown "Keycloak" → "Create realm" → Nome: `sobrevidas` → Create

**Criar o Client:**
1. Clients → "Create client" → Client ID: `pacientes-api` → Next
2. Habilite "Client authentication" → Next → Save
3. Aba Credentials → copie o **Client Secret**

**Criar usuário de teste:**
1. Users → "Create new user"
2. Username: `usuario-teste`, Email: `teste@sobrevidas.com`
3. Preencha First name e Last name → ligue "Email verified" → Create
4. Aba Credentials → "Set password" → senha: `senha123` → desligue "Temporary" → Save

### 4. Rode a aplicação
```bash
./mvnw spring-boot:run
```
> A aplicação importa automaticamente os 68 pacientes do CSV na primeira execução.

### 5. Obtenha o token de acesso (PowerShell)
```powershell
$response = Invoke-RestMethod -Method Post `
  -Uri "http://localhost:8180/realms/sobrevidas/protocol/openid-connect/token" `
  -ContentType "application/x-www-form-urlencoded" `
  -Body @{
    grant_type    = "password"
    client_id     = "pacientes-api"
    client_secret = "SEU_CLIENT_SECRET"
    username      = "usuario-teste"
    password      = "senha123"
  }

$response.access_token
```

### 6. Acesse o Swagger
1. Acesse http://localhost:8080/swagger-ui/index.html
2. Clique em **Authorize** 🔒 → cole o token → Authorize → Close

## Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | /pacientes | Lista todos os pacientes |
| GET | /pacientes/{id} | Busca por ID |
| GET | /pacientes/busca?nome= | Busca por nome |
| GET | /pacientes/busca?cidade= | Busca por cidade |
| GET | /pacientes/busca?estado= | Busca por estado |
| GET | /pacientes/busca?sexo= | Busca por sexo |
| GET | /pacientes/busca?ehTabagista= | Busca por tabagismo |
| POST | /pacientes | Cria novo paciente |
| PUT | /pacientes/{id} | Atualiza paciente completo |
| DELETE | /pacientes/{id} | Remove paciente |

> Todos os endpoints requerem autenticação via Bearer token JWT.

## Rodar testes
```bash
./mvnw test
```
> Os testes de integração usam Testcontainers — Docker Desktop deve estar rodando.

## Observações técnicas
- O ID do paciente é gerado automaticamente pelo banco (auto-increment)
- O POST e PUT recebem DTOs (`PostRequestBody` / `PutRequestBody`) — a entidade não é exposta diretamente
- A importação do CSV é idempotente: roda apenas se o banco estiver vazio
- O Keycloak precisa ser reconfigurado após reiniciar os containers