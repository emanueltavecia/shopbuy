package com.shop.buy.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("API de Gerenciamento de Loja de Roupas")
                .description(
                    "API RESTful para Sistema de Gerenciamento de Loja de Roupas. Esta API fornece endpoints para gerenciar produtos, categorias, marcas, clientes, funcionários, vendas e fornecedores.")
                .version("1.0.0"))
        .externalDocs(
            new ExternalDocumentation()
                .description("Documentação do Sistema de Gerenciamento de Loja de Roupas"))
        .servers(Arrays.asList(new Server().url("/").description("URL do Servidor Padrão")))
        .addTagsItem(new Tag().name("Marcas").description("Operações relacionadas a marcas"))
        .addTagsItem(
            new Tag()
                .name("Categorias")
                .description("Operações relacionadas a categorias de produtos"))
        .addTagsItem(new Tag().name("Produtos").description("Operações relacionadas a produtos"))
        .addTagsItem(new Tag().name("Clientes").description("Operações relacionadas a clientes"))
        .addTagsItem(
            new Tag().name("Funcionários").description("Operações relacionadas a funcionários"))
        .addTagsItem(new Tag().name("Vendas").description("Operações relacionadas a vendas"))
        .addTagsItem(
            new Tag().name("Itens de Venda").description("Operações relacionadas a itens de venda"))
        .addTagsItem(
            new Tag().name("Fornecedores").description("Operações relacionadas a fornecedores"));
  }
}
