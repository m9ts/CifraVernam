package com.segi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI cifraOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API de Cifra de Vernan")
                .description("API para cifrar e decifrar mensagens usando a Cifra de Vernan. Texto cifrado deve ser retornado em binário, com formato de 8 bits.")
                .version("1.0.1"));
    }
}
