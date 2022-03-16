package com.legallync.userservice.config;


import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@EnableSwagger2
@Configuration
public class SwaggerConfig {



    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("user-service").select()
                .apis(RequestHandlerSelectors.basePackage("com.legallync.userservice.controller"))
                .paths(PathSelectors.regex("/.*")).build().apiInfo(apiInfo())
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("user-Service".replaceAll("-", " ").toUpperCase(),
                "User Management Service For Legallync", "v1", "Terms of service",
                new Contact("Jeremiah Okorie Imo", "https://legallync.com", "legallync@gmail.com"),
                "License of API", "licensce", Collections.emptyList());
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().scopeSeparator(",")
                .useBasicAuthenticationWithAccessCodeGrant(false).build();
    }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("apiKey", authorizationScopes));
    }
}