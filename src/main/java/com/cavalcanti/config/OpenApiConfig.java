package com.cavalcanti.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Igor Cavalcanti",
                        email = "contact@igorcavalcanti.com",
                        url = "https://igorcavalcanti.com"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "Spring Boot 3 - Spring Security 6 App",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://igorcavalcanti.com/api"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
//@SecurityScheme(
//        name = "bearerAuth",
//        description = "JWT auth description",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
public class OpenApiConfig {
}

//
//package com.cavalcanti.spring.swagger.config;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Contact;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.servers.Server;
//
//@Configuration
//public class OpenAPIConfig {
//
//  @Value("${cavalcanti.openapi.dev-url}")
//  private String devUrl;
//
//  @Value("${cavalcanti.openapi.prod-url}")
//  private String prodUrl;
//
//  @Bean
//  public OpenAPI myOpenAPI() {
//    Server devServer = new Server();
//    devServer.setUrl(devUrl);
//    devServer.setDescription("Server URL in Development environment");
//
//    Server prodServer = new Server();
//    prodServer.setUrl(prodUrl);
//    prodServer.setDescription("Server URL in Production environment");
//
//    Contact contact = new Contact();
//    contact.setEmail("cavalcanti@gmail.com");
//    contact.setName("cavalcanti");
//    contact.setUrl("https://www.igorcavalcanti.com");
//
//    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
//
//    Info info = new Info()
//        .title("Tutorial Management API")
//        .version("1.0")
//        .contact(contact)
//        .description("This API exposes endpoints to manage tutorials.").termsOfService("https://www.igorcavalcanti.com/terms")
//        .license(mitLicense);
//
//    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
//  }
//}
//
//https://github.com/springdoc/springdoc-openapi
//In application.properties file:
//
//cavalcanti.openapi.dev-url=http://localhost:8080
//cavalcanti.openapi.prod-url=https://cavalcanti-api.com

//springdoc.api-docs.enabled=false
//springdoc.swagger-ui.enabled=false

//springdoc.swagger-ui.path=/cavalcanti-documentation
//springdoc.api-docs.path=/cavalcanti-api-docs
//
//springdoc.packages-to-scan=com.cavalcanti.controller
//springdoc.swagger-ui.tryItOutEnabled=true
//springdoc.swagger-ui.operationsSorter=method
//springdoc.swagger-ui.tagsSorter=alpha
//springdoc.swagger-ui.filter=true