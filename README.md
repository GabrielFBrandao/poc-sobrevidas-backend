# Pacientes API

API REST para gerenciamento de pacientes, desenvolvida com Java 17 + Spring Boot + PostgreSQL.

## Tecnologias
- Java 17
- Spring Boot 3.5
- Spring Data JPA
- PostgreSQL (via Docker)
- Swagger / OpenAPI 3
- JUnit 5 + Mockito
- Lombok
- OpenCSV

## ▶Como rodar

### Pré-requisitos
- Java 17+
- Docker Desktop

### 1. Clone o repositório
```bash
git clone https://github.com/GabrielFBrandao/poc-sobrevidas-backend.git
cd poc-sobrevidas-backend
```

### 2. Suba o banco de dados
```bash
docker-compose up -d
```

### 3. Rode a aplicação
```bash
./mvnw spring-boot:run
```

A aplicação irá automaticamente importar os 68 pacientes do CSV para o banco na primeira execução.

### 4. Acesse
- API: http://localhost:8080/pacientes
- Swagger: http://localhost:8080/swagger-ui/index.html

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

## Rodar testes
```bash
./mvnw test
```
