package br.com.sobrevidas.pacientesapi.service;

import br.com.sobrevidas.pacientesapi.model.Paciente;
import br.com.sobrevidas.pacientesapi.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteService service;

    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = Paciente.builder()
                .id(1L)
                .nome("João Silva")
                .cpf("12345678900")
                .sexo("MASCULINO")
                .cidade("Goiânia")
                .estado("GO")
                .ehTabagista(false)
                .ehEtilista(false)
                .temLesaoSuspeita(false)
                .participaSmartMonitor(true)
                .build();
    }

    @Test
    void deveListarTodosPacientes() {
        when(repository.findAll()).thenReturn(List.of(paciente));

        List<Paciente> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarPacientePorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(paciente));

        Optional<Paciente> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().getNome());
    }

    @Test
    void deveRetornarVazioQuandoIdNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Paciente> resultado = service.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveCriarPaciente() {
        when(repository.save(paciente)).thenReturn(paciente);

        Paciente resultado = service.criar(paciente);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(repository, times(1)).save(paciente);
    }

    @Test
    void deveDeletarPacienteExistente() {
        when(repository.existsById(1L)).thenReturn(true);

        boolean resultado = service.deletar(1L);

        assertTrue(resultado);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveRetornarFalseAoDeletarPacienteInexistente() {
        when(repository.existsById(99L)).thenReturn(false);

        boolean resultado = service.deletar(99L);

        assertFalse(resultado);
        verify(repository, never()).deleteById(any());
    }

    @Test
    void deveBuscarPorNome() {
        when(repository.findByNomeContainingIgnoreCase("João"))
                .thenReturn(List.of(paciente));

        List<Paciente> resultado = service.buscarPorNome("João");

        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void deveBuscarPorCidade() {
        when(repository.findByCidade("Goiânia")).thenReturn(List.of(paciente));

        List<Paciente> resultado = service.buscarPorCidade("Goiânia");

        assertEquals(1, resultado.size());
    }
}