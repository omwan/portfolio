package com.omwan.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration for swagger API documentation.
 *
 * Created by olivi on 07/16/2017.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.ant("/api/**"))
            .build()
            .apiInfo(buildApiInfo());
  }

  private ApiInfo buildApiInfo() {
    return new ApiInfoBuilder()
            .contact(new Contact("Olivia Wan", "https://github.com/omwan", "wan.o@husky.neu.edu"))
            .description("API documentation for Java/Spring REST services to manage data regarding my projects")
            .title("Portfolio Project")
            .build();
  }
}
