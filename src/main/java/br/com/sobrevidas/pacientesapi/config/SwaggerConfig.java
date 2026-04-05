package br.com.sobrevidas.pacientesapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Pacientes API", version = "v1",
                 description = "API REST para gerenciamento de pacientes - Sobrevidas"),
    security = @SecurityRequirement(name = "bearerAuth")
)
public class SwaggerConfig {
}