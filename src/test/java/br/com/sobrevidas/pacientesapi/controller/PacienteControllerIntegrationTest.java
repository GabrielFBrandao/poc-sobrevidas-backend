package br.com.sobrevidas.pacientesapi.controller;

import br.com.sobrevidas.pacientesapi.model.Paciente;
import br.com.sobrevidas.pacientesapi.repository.PacienteRepository;
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
        Paciente novo = Paciente.builder()
                .id(999L)
                .nome("Paciente Integração")
                .cpf("12345678900")
                .sexo("MASCULINO")
                .cidade("Goiânia")
                .estado("GO")
                .participaSmartMonitor(true)
                .build();

        mockMvc.perform(post("/pacientes")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Paciente Integração"))
                .andExpect(jsonPath("$.cidade").value("Goiânia"));
    }

    @Test
    void deveBuscarPacientePorId() throws Exception {
        Paciente salvo = repository.save(Paciente.builder()
                .id(1L)
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
        repository.save(Paciente.builder()
                .id(2L).nome("Nome Antigo")
                .cpf("11122233344").build());

        Paciente atualizado = Paciente.builder()
                .id(2L).nome("Nome Atualizado")
                .cpf("11122233344").cidade("Goiânia").build();

        mockMvc.perform(put("/pacientes/2")
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Atualizado"));
    }

    @Test
    void deveDeletarPaciente() throws Exception {
        repository.save(Paciente.builder()
                .id(3L).nome("Para Deletar")
                .cpf("55566677788").build());

        mockMvc.perform(delete("/pacientes/3")
                .with(jwt()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/pacientes/3")
                .with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRetornar401SemAutenticacao() throws Exception {
        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isUnauthorized());
    }
}