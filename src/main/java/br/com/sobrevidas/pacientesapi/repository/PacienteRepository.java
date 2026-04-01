// Camada de persistência/Dados - abstrai a comunicação com o banco de dados, 
//permitindo operações CRUD (Create, Read, Update, Delete) e consultas personalizadas.

package br.com.sobrevidas.pacientesapi.repository;

import br.com.sobrevidas.pacientesapi.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    List<Paciente> findByNomeContainingIgnoreCase(String nome);

    List<Paciente> findByCidade(String cidade);

    List<Paciente> findByEstado(String estado);

    List<Paciente> findBySexo(String sexo);

    List<Paciente> findByEhTabagista(Boolean ehTabagista);

    List<Paciente> findByEhEtilista(Boolean ehEtilista);

    List<Paciente> findByTemLesaoSuspeita(Boolean temLesaoSuspeita);

}
