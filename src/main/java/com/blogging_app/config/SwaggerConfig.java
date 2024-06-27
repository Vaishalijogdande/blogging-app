package com.blogging_app.config;

import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blogging Application")
                        .description("This project is developed by Vaishali Jogdande")
                        .version("1.0")
                        .termsOfService("Terms of Service")
                        .contact(new Contact()
                                .name("Vaishali Jogdande")
                                .email("vaishalijogdande@gmail.com"))
                        .license(new License()
                                .name("License of API")
                                .url("API License URL")))

                .addSecurityItem(new SecurityRequirement().addList("bearer"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearer", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }


}
