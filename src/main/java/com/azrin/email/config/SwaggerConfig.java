package com.azrin.email.config;
import com.azrin.email.utils.ApiVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.metadata.title}")
    private String title;

    @Value("${swagger.metadata.description}")
    private String description;

    @Value("${swagger.metadata.version}")
    private String version;

    @Value("${swagger.metadata.terms}")
    private String terms;

    @Value("${swagger.metadata.contact.name}")
    private String contactName;

    @Value("${swagger.metadata.contact.url}")
    private String contactUrl;

    @Value("${swagger.metadata.contact.email}")
    private String contactEmail;

    @Value("${swagger.metadata.licence}")
    private String licence;

    @Value("${swagger.metadata.licence.url}")
    private String licenceUrl;

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.azrin.email.controller"))
                .paths(any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(contactName, contactUrl, contactEmail);
        return new ApiInfo(title, description, version, terms, contact, licence, licenceUrl, new ArrayList());
    }
}