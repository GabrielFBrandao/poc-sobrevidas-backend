// Camada de Apresentação/Interface - recebe requisições HTTP(JSON/XML), valida os dados e
//chama o Service, retornando a resposta final.

package br.com.sobrevidas.pacientesapi.controller;

import br.com.sobrevidas.pacientesapi.model.Paciente;
import br.com.sobrevidas.pacientesapi.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;

    // GET /pacientes → lista todos
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // GET /pacientes/{id} → busca por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /pacientes/busca?nome=João
    @GetMapping("/busca")
    public ResponseEntity<List<Paciente>> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) Boolean ehTabagista,
            @RequestParam(required = false) Boolean ehEtilista,
            @RequestParam(required = false) Boolean temLesaoSuspeita) {

        if (nome != null)             return ResponseEntity.ok(service.buscarPorNome(nome));
        if (cidade != null)           return ResponseEntity.ok(service.buscarPorCidade(cidade));
        if (estado != null)           return ResponseEntity.ok(service.buscarPorEstado(estado));
        if (sexo != null)             return ResponseEntity.ok(service.buscarPorSexo(sexo));
        if (ehTabagista != null)      return ResponseEntity.ok(service.buscarTabagistas(ehTabagista));
        if (ehEtilista != null)       return ResponseEntity.ok(service.buscarEtilistas(ehEtilista));
        if (temLesaoSuspeita != null) return ResponseEntity.ok(service.buscarComLesaoSuspeita(temLesaoSuspeita));

        return ResponseEntity.ok(service.listarTodos());
    }

    // POST /pacientes → cria novo paciente
    @PostMapping
    public ResponseEntity<Paciente> criar(@RequestBody Paciente paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(paciente));
    }

    // PUT /pacientes/{id} → atualiza paciente completo
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id,
                                               @RequestBody Paciente paciente) {
        return service.atualizar(id, paciente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /pacientes/{id} → remove paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}