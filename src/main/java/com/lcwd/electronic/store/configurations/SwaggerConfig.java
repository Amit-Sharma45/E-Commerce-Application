package com.lcwd.electronic.store.configurations;


import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());
        docket.securityContexts(Arrays.asList(getSecurityContext()));
        docket.securitySchemes(Arrays.asList(getSchemes()));

        ApiSelectorBuilder builder = docket.select();
        builder.apis(RequestHandlerSelectors.any());
        builder.paths(PathSelectors.any());
        Docket build = builder.build();
        return build;
    }

    private SecurityContext getSecurityContext() {
        SecurityContext context = SecurityContext.builder()
                .securityReferences(getSecurityReferences())
                .build();
        return context;
    }

    private List<SecurityReference> getSecurityReferences() {
        AuthorizationScope[] scopes = {new AuthorizationScope("Global", "Access Every Thing")};
        return Arrays.asList(new SecurityReference("JWT", scopes));
    }

    private ApiKey getSchemes() {
        return new ApiKey("JWT", "Authorization", "header");
    }


    private ApiInfo getApiInfo() {
        ApiInfo info = new ApiInfo(
                "Electronic Store Backend : APIS",
                "This is backend project created by BikkadIT08",
                "1.0.0v",
                "https://www.learncodewithdurgesh.com",
                new Contact("Amit", "https://www.instagram.com/akku_sharma_4_5", "sharma4585amit@gmail.com"),
                "License of APIS",
                "https://www.learncodewithdurgesh.com/about",
                new ArrayDeque<>()
        );
        return info;
    }
}
