# Pacientes API

API REST para gerenciamento de pacientes, desenvolvida com Java 17 + Spring Boot + PostgreSQL + Keycloak.

## Tecnologias
- Java 17
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL (via Docker)
- Keycloak 24 (via Docker)
- Spring Security + OAuth2
- Swagger / OpenAPI 3
- JUnit 5 + Mockito
- Lombok
- OpenCSV

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
1. Clique no dropdown "Keycloak" → "Create realm"
2. Nome: `sobrevidas` → "Create"

**Criar o Client:**
1. Clients → "Create client"
2. Client ID: `pacientes-api` → Next
3. Habilite "Client authentication" → Next → Save
4. Vá em Credentials e copie o **Client Secret**

**Criar usuário de teste:**
1. Users → "Create new user"
2. Username: `usuario-teste`, Email: `teste@sobrevidas.com`
3. Ligue "Email verified" → Create
4. Aba Credentials → "Set password" → senha: `senha123`
5. Desligue "Temporary" → Save
6. Aba Details → preencha First name e Last name → Save

### 4. Rode a aplicação
```bash
./mvnw spring-boot:run
```

### 5. Obtenha o token de acesso
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

### 6. Acesse o Swagger e autorize
1. Acesse http://localhost:8080/swagger-ui/index.html
2. Clique em **"Authorize"** 
3. Cole o token gerado no passo anterior
4. Clique "Authorize" → "Close"

Agora todos os endpoints estão disponíveis!

## Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | /pacientes | Lista todos os pacientes |
| GET | /pacientes/{id} | Busca paciente por ID |
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