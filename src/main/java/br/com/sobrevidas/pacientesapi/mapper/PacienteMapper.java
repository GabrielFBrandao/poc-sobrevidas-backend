package br.com.sobrevidas.pacientesapi.mapper;

import br.com.sobrevidas.pacientesapi.model.Paciente;
import br.com.sobrevidas.pacientesapi.request.PacientePostRequestBody;
import br.com.sobrevidas.pacientesapi.request.PacientePutRequestBody;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public Paciente toEntity(PacientePostRequestBody body) {
        return Paciente.builder()
                .nome(body.getNome())
                .cpf(body.getCpf())
                .dataNascimento(body.getDataNascimento())
                .sexo(body.getSexo())
                .email(body.getEmail())
                .telefoneCelular(body.getTelefoneCelular())
                .telefoneResponsavel(body.getTelefoneResponsavel())
                .nomeMae(body.getNomeMae())
                .numCartaoSus(body.getNumCartaoSus())
                .cep(body.getCep())
                .endereco(body.getEndereco())
                .numEndereco(body.getNumEndereco())
                .complemento(body.getComplemento())
                .bairro(body.getBairro())
                .cidade(body.getCidade())
                .estado(body.getEstado())
                .ehTabagista(body.getEhTabagista())
                .ehEtilista(body.getEhEtilista())
                .temLesaoSuspeita(body.getTemLesaoSuspeita())
                .participaSmartMonitor(body.getParticipaSmartMonitor())
                .build();
    }

    public void updateEntity(Paciente paciente, PacientePutRequestBody body) {
        paciente.setNome(body.getNome());
        paciente.setCpf(body.getCpf());
        paciente.setDataNascimento(body.getDataNascimento());
        paciente.setSexo(body.getSexo());
        paciente.setEmail(body.getEmail());
        paciente.setTelefoneCelular(body.getTelefoneCelular());
        paciente.setTelefoneResponsavel(body.getTelefoneResponsavel());
        paciente.setNomeMae(body.getNomeMae());
        paciente.setNumCartaoSus(body.getNumCartaoSus());
        paciente.setCep(body.getCep());
        paciente.setEndereco(body.getEndereco());
        paciente.setNumEndereco(body.getNumEndereco());
        paciente.setComplemento(body.getComplemento());
        paciente.setBairro(body.getBairro());
        paciente.setCidade(body.getCidade());
        paciente.setEstado(body.getEstado());
        paciente.setEhTabagista(body.getEhTabagista());
        paciente.setEhEtilista(body.getEhEtilista());
        paciente.setTemLesaoSuspeita(body.getTemLesaoSuspeita());
        paciente.setParticipaSmartMonitor(body.getParticipaSmartMonitor());
    }
}