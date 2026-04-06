package br.com.sobrevidas.pacientesapi.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PacientePutRequestBody {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String sexo;
    private String email;
    private String telefoneCelular;
    private String telefoneResponsavel;
    private String nomeMae;
    private String numCartaoSus;
    private String cep;
    private String endereco;
    private String numEndereco;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private Boolean ehTabagista;
    private Boolean ehEtilista;
    private Boolean temLesaoSuspeita;
    private Boolean participaSmartMonitor;
}