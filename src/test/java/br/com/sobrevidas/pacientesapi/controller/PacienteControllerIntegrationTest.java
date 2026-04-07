package br.com.sobrevidas.pacientesapi.controller;

import br.com.sobrevidas.pacientesapi.model.Paciente;
import br.com.sobrevidas.pacientesapi.repository.PacienteRepository;
import br.com.sobrevidas.pacientesapi.request.PacientePostRequestBody;
import br.com.sobrevidas.pacientesapi.request.PacientePutRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
class PacienteControllerIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> "http://dummy-issuer");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacienteRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaPacientes() throws Exception {
        mockMvc.perform(get("/pacientes")
                .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void deveCriarPacienteComSucesso() throws Exception {
        PacientePostRequestBody body = new PacientePostRequestBody();
        body.setNome("Paciente Integração");
        body.setCpf("12345678900");
        body.setSexo("MASCULINO");
        body.setCidade("Goiânia");
        body.setEstado("GO");
        body.setParticipaSmartMonitor(true);

        mockMvc.perform(post("/pacientes")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Paciente Integração"))
                .andExpect(jsonPath("$.cidade").value("Goiânia"));
    }

    @Test
    void deveBuscarPacientePorId() throws Exception {
        Paciente salvo = repository.save(Paciente.builder()
                .nome("Maria Silva")
                .cpf("98765432100")
                .cidade("Brasília")
                .estado("DF")
                .build());

        mockMvc.perform(get("/pacientes/" + salvo.getId())
                .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria Silva"));
    }

    @Test
    void deveRetornar404QuandoPacienteNaoExiste() throws Exception {
        mockMvc.perform(get("/pacientes/99999")
                .with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarPaciente() throws Exception {
        Paciente salvo = repository.save(Paciente.builder()
                .nome("Nome Antigo")
                .cpf("11122233344")
                .build());

        PacientePutRequestBody body = new PacientePutRequestBody();
        body.setNome("Nome Atualizado");
        body.setCpf("11122233344");
        body.setCidade("Goiânia");

        mockMvc.perform(put("/pacientes/" + salvo.getId())
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Atualizado"));
    }

    @Test
    void deveDeletarPaciente() throws Exception {
        Paciente salvo = repository.save(Paciente.builder()
                .nome("Para Deletar")
                .cpf("55566677788")
                .build());

        mockMvc.perform(delete("/pacientes/" + salvo.getId())
                .with(jwt()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/pacientes/" + salvo.getId())
                .with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar401SemAutenticacao() throws Exception {
        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isUnauthorized());
    }
}