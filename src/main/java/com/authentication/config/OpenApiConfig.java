package com.authentication.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
info = @Info(
        contact = @Contact(
                name = "Sinebi Innazo",
                email = "sinebi.innazo@gmail.com",
                url = "https://www.panteka.com"
            ),
        description = "Backend Api Documentation for Panteka",
        title = "Panteka Backend Api",
        version = "1.0",
        termsOfService = "https://www.panteka.com"
    ),
servers = {
        @Server(
                description = "DEVELOPMENT ENV",
                url = "http://67.217.58.16:7071"
            )
    },
security = {
        @SecurityRequirement(
                name = "bearerAuth")
    }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
