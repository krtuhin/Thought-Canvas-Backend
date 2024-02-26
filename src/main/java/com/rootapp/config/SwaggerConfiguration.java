package com.rootapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Thought-Canvas-Backend", description = "Backend for Blog-Application using Spring Boot", version = "1.0.0", termsOfService = "Terms of Service", contact = @Contact(name = "Tuhin Kumar", email = "tuhin@gmail.com", url = "https://www.example.com"), license = @License(name = "License of APIs", url = "API license URLs")), servers = {
                @Server(description = "DEV", url = "http://127.0.0.1:8080"),
                @Server(description = "PROD", url = "server url") }, security = @SecurityRequirement(name = "bearerAuthorization"))

@SecurityScheme(name = "bearerAuthorization", description = "Security Description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)

public class SwaggerConfiguration {

}
