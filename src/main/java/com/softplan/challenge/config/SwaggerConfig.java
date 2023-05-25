package com.softplan.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI config() {
        return new OpenAPI().info(
                        new Info().title("SoftPlan Challenge API").description("Rest API").version("v0.0.1"));

    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("API - public").pathsToMatch("/**").build();
    }

}
