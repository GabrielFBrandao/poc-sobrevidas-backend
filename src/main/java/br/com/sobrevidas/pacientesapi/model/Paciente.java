// Entidade/Domínio - representa a estrutura dos dados, espelhando as tabelas do banco de dados. 
// Cada instância dessa classe corresponde a um registro na tabela "pacientes".
package br.com.sobrevidas.pacientesapi.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone_celular")
    private String telefoneCelular;

    @Column(name = "telefone_responsavel")
    private String telefoneResponsavel;

    @Column(name = "nome_mae")
    private String nomeMae;

    @Column(name = "num_cartao_sus")
    private String numCartaoSus;

    @Column(name = "cep")
    private String cep;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "num_endereco")
    private String numEndereco;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "eh_tabagista")
    private Boolean ehTabagista;

    @Column(name = "eh_etilista")
    private Boolean ehEtilista;

    @Column(name = "tem_lesao_suspeita")
    private Boolean temLesaoSuspeita;

    @Column(name = "participa_smart_monitor")
    private Boolean participaSmartMonitor;

}
