package com.seek.candidates.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
    info = @Info(
            title = "Sistema de Gestión de Candidatos en Proceso de Selección y Contratación",
            description = "API REST utilizando Spring Boot con microservicios para realizar operaciones CRUD en la base de datos",
            version = "1.0.0",
            contact = @Contact(
                name = "Richard Palomino",
                url = "https://github.com/ricpalomino/candidates",
                email = "richard.palomino.tec@gmail.com"
            )
    ),
    servers = {
        @Server(
            description = "DEV SERVER",
            url = "http://localhost:8080"
        ),
        @Server(
            description = "PUBLISH SERVER",
            url = "https://ms-candidates-latest.onrender.com"
        )
    },
    security = @SecurityRequirement(
            name = "Security Token"
    )
)
@SecurityScheme(
    name = "Security Token",
    description = "Access Token For API",
    type = SecuritySchemeType.HTTP,
    paramName = HttpHeaders.AUTHORIZATION,
    in = SecuritySchemeIn.HEADER,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {

}
