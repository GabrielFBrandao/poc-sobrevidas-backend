// Camada de Regra de Negócio - contém a lógica de negócio principal, validações complexas e orquestra o fluxo
//de dados entre o controlador e o repositório.

package br.com.sobrevidas.pacientesapi.service;

import br.com.sobrevidas.pacientesapi.model.Paciente;
import br.com.sobrevidas.pacientesapi.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;

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

    public Paciente criar(Paciente paciente) {
        return repository.save(paciente);
    }

    public Optional<Paciente> atualizar(Long id, Paciente dadosNovos) {
        return repository.findById(id).map(paciente -> {
            paciente.setNome(dadosNovos.getNome());
            paciente.setCpf(dadosNovos.getCpf());
            paciente.setDataNascimento(dadosNovos.getDataNascimento());
            paciente.setSexo(dadosNovos.getSexo());
            paciente.setEmail(dadosNovos.getEmail());
            paciente.setTelefoneCelular(dadosNovos.getTelefoneCelular());
            paciente.setTelefoneResponsavel(dadosNovos.getTelefoneResponsavel());
            paciente.setNomeMae(dadosNovos.getNomeMae());
            paciente.setNumCartaoSus(dadosNovos.getNumCartaoSus());
            paciente.setCep(dadosNovos.getCep());
            paciente.setEndereco(dadosNovos.getEndereco());
            paciente.setNumEndereco(dadosNovos.getNumEndereco());
            paciente.setComplemento(dadosNovos.getComplemento());
            paciente.setBairro(dadosNovos.getBairro());
            paciente.setCidade(dadosNovos.getCidade());
            paciente.setEstado(dadosNovos.getEstado());
            paciente.setEhTabagista(dadosNovos.getEhTabagista());
            paciente.setEhEtilista(dadosNovos.getEhEtilista());
            paciente.setTemLesaoSuspeita(dadosNovos.getTemLesaoSuspeita());
            paciente.setParticipaSmartMonitor(dadosNovos.getParticipaSmartMonitor());
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