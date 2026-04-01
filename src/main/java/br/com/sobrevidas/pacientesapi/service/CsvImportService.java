package br.com.sobrevidas.pacientesapi.service;

import br.com.sobrevidas.pacientesapi.model.Paciente;
import br.com.sobrevidas.pacientesapi.repository.PacienteRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvImportService {

    private final PacienteRepository repository;

    @PostConstruct
    public void importar() {
        if (repository.count() > 0) {
            log.info("Banco já populado, pulando importação do CSV.");
            return;
        }

        try {
            var resource = new ClassPathResource("pacientes.csv");
            var parser = new CSVParserBuilder().withSeparator(',').build();
            var reader = new CSVReaderBuilder(new InputStreamReader(resource.getInputStream()))
                    .withCSVParser(parser)
                    .build();

            var linhas = reader.readAll();
            var cabecalho = linhas.get(0);
            log.info("Iniciando importação de {} pacientes...", linhas.size() - 1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter formatterSemHora = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (int i = 1; i < linhas.size(); i++) {
                try {
                    String[] linha = linhas.get(i);

                    LocalDate dataNascimento = null;
                    String dataStr = get(linha, 0);
                    if (dataStr != null && !dataStr.isBlank()) {
                        try {
                            dataNascimento = LocalDate.parse(dataStr.trim(), formatter);
                        } catch (DateTimeParseException e) {
                            dataNascimento = LocalDate.parse(dataStr.trim(), formatterSemHora);
                        }
                    }

                    Paciente paciente = Paciente.builder()
                            .dataNascimento(dataNascimento)
                            .id(parseLong(get(linha, 1)))
                            .cpf(get(linha, 2))
                            .cep(get(linha, 3))
                            .numCartaoSus(get(linha, 4))
                            .telefoneCelular(get(linha, 5))
                            .telefoneResponsavel(get(linha, 6))
                            .nomeMae(get(linha, 7))
                            .complemento(get(linha, 8))
                            .email(get(linha, 9))
                            .nome(get(linha, 10))
                            .sexo(get(linha, 11))
                            .endereco(get(linha, 12))
                            .numEndereco(get(linha, 13))
                            .estado(get(linha, 14))
                            .cidade(get(linha, 15))
                            .ehTabagista(parseBoolean(get(linha, 16)))
                            .ehEtilista(parseBoolean(get(linha, 17)))
                            .temLesaoSuspeita(parseBoolean(get(linha, 18)))
                            .bairro(get(linha, 19))
                            .participaSmartMonitor(parseBoolean(get(linha, 20)))
                            .build();

                    repository.save(paciente);

                } catch (Exception e) {
                    log.warn("Erro ao importar linha {}: {}", i + 1, e.getMessage());
                }
            }

            log.info("Importação concluída!");

        } catch (Exception e) {
            log.error("Erro ao ler CSV: {}", e.getMessage());
        }
    }

    private String get(String[] linha, int index) {
        if (index >= linha.length) return null;
        String val = linha[index].trim();
        return (val.isEmpty() || val.equalsIgnoreCase("nan") || val.equalsIgnoreCase("null")) ? null : val;
    }

    private Long parseLong(String val) {
        try { return val == null ? null : Long.parseLong(val); }
        catch (NumberFormatException e) { return null; }
    }

    private Boolean parseBoolean(String val) {
        if (val == null) return null;
        return val.equalsIgnoreCase("true") || val.equalsIgnoreCase("1");
    }
}
