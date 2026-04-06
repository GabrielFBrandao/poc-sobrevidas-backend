package br.com.sobrevidas.pacientesapi.service;

import br.com.sobrevidas.pacientesapi.mapper.PacienteMapper;
import br.com.sobrevidas.pacientesapi.model.Paciente;
import br.com.sobrevidas.pacientesapi.repository.PacienteRepository;
import br.com.sobrevidas.pacientesapi.request.PacientePostRequestBody;
import br.com.sobrevidas.pacientesapi.request.PacientePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;
    private final PacienteMapper mapper;

    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Paciente> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Paciente> buscarPorCidade(String cidade) {
        return repository.findByCidade(cidade);
    }

    public List<Paciente> buscarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    public List<Paciente> buscarPorSexo(String sexo) {
        return repository.findBySexo(sexo);
    }

    public List<Paciente> buscarTabagistas(Boolean ehTabagista) {
        return repository.findByEhTabagista(ehTabagista);
    }

    public List<Paciente> buscarEtilistas(Boolean ehEtilista) {
        return repository.findByEhEtilista(ehEtilista);
    }

    public List<Paciente> buscarComLesaoSuspeita(Boolean temLesaoSuspeita) {
        return repository.findByTemLesaoSuspeita(temLesaoSuspeita);
    }

    public Paciente criar(PacientePostRequestBody body) {
        Paciente paciente = mapper.toEntity(body);
        return repository.save(paciente);
    }

    public Optional<Paciente> atualizar(Long id, PacientePutRequestBody body) {
        return repository.findById(id).map(paciente -> {
            mapper.updateEntity(paciente, body);
            return repository.save(paciente);
        });
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}