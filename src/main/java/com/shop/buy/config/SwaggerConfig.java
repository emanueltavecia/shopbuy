package com.shop.buy.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clothing Store Management API")
                        .description("RESTful API for a Clothing Store Management System. This API provides endpoints to manage products, categories, brands, customers, employees, sales and suppliers.")
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Clothing Store Management Documentation"))
                .servers(Arrays.asList(
                        new Server().url("/").description("Default Server URL")))
                .addTagsItem(new Tag().name("Brands").description("Operations related to brands"))
                .addTagsItem(new Tag().name("Categories").description("Operations related to product categories"))
                .addTagsItem(new Tag().name("Products").description("Operations related to products"))
                .addTagsItem(new Tag().name("Customers").description("Operations related to customers"))
                .addTagsItem(new Tag().name("Employees").description("Operations related to employees"))
                .addTagsItem(new Tag().name("Sales").description("Operations related to sales"))
                .addTagsItem(new Tag().name("Sale Items").description("Operations related to sale items"))
                .addTagsItem(new Tag().name("Suppliers").description("Operations related to suppliers"));
    }
}
