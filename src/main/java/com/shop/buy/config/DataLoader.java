package com.shop.buy.config;

import com.shop.buy.model.*;
import com.shop.buy.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
  @Value("${server.port:8081}")
  private String serverPort;

  private String getServerSuccessfullyStartedMessage() {
    return "Access http://localhost:" + serverPort + "/swagger-ui.html see the docs";
  }

  @Bean
  public CommandLineRunner initDatabase(
      CategoryRepository categoryRepository,
      BrandRepository brandRepository,
      ProductRepository productRepository,
      CustomerRepository customerRepository,
      EmployeeRepository employeeRepository,
      SupplierRepository supplierRepository,
      SaleRepository saleRepository,
      SaleItemRepository saleItemRepository) {

    return args -> {
      // Verificar se já existem dados antes de inserir
      if (brandRepository.count() > 0) {
        System.out.println("Database already has data, skipping initialization");
        System.out.println(getServerSuccessfullyStartedMessage());
        return;
      }

      // cadastrar marcas
      Brand nike = new Brand();
      nike.setName("Nike");
      nike.setCountry("Estados Unidos");
      nike.setDescription("Roupas esportivas e atléticas");
      brandRepository.save(nike);

      Brand adidas = new Brand();
      adidas.setName("Adidas");
      adidas.setCountry("Alemanha");
      adidas.setDescription("Roupas esportivas e casuais");
      brandRepository.save(adidas);

      Brand puma = new Brand();
      puma.setName("Puma");
      puma.setCountry("Alemanha");
      puma.setDescription("Produtos esportivos e de estilo de vida");
      brandRepository.save(puma);

      // cadastrar categorias
      Category shirts = new Category();
      shirts.setName("Camisetas");
      shirts.setDescription("Todos os tipos de camisas e camisetas");
      categoryRepository.save(shirts);

      Category pants = new Category();
      pants.setName("Calças");
      pants.setDescription("Jeans, calças e outras roupas para pernas");
      categoryRepository.save(pants);

      Category shoes = new Category();
      shoes.setName("Sapatos");
      shoes.setDescription("Calçados de todos os tipos");
      categoryRepository.save(shoes);

      // cadastrar fornecedores
      Supplier supplier1 = new Supplier();
      supplier1.setName("Mundo dos Tecidos");
      supplier1.setCnpj("12.345.678/0001-90");
      supplier1.setPhone("(11) 3333-4444");
      supplier1.setEmail("contato@mundodostecidos.com");
      supplierRepository.save(supplier1);

      Supplier supplier2 = new Supplier();
      supplier2.setName("Soluções Têxteis");
      supplier2.setCnpj("98.765.432/0001-10");
      supplier2.setPhone("(11) 5555-6666");
      supplier2.setEmail("info@solucoestexteis.com");
      supplierRepository.save(supplier2);

      // cadastrar produtos
      Product product1 = new Product();
      product1.setName("Camiseta Esportiva");
      product1.setSize("M");
      product1.setColor("Azul");
      product1.setPrice(new BigDecimal("29.99"));
      product1.setCategory(shirts);
      product1.setBrand(nike);
      product1.setSupplier(supplier1);
      productRepository.save(product1);

      Product product2 = new Product();
      product2.setName("Tênis de Corrida");
      product2.setSize("42");
      product2.setColor("Preto");
      product2.setPrice(new BigDecimal("89.99"));
      product2.setCategory(shoes);
      product2.setBrand(adidas);
      product2.setSupplier(supplier2);
      productRepository.save(product2);

      Product product3 = new Product();
      product3.setName("Calça Jeans 501");
      product3.setSize("32");
      product3.setColor("Azul");
      product3.setPrice(new BigDecimal("79.99"));
      product3.setCategory(pants);
      product3.setBrand(puma);
      product3.setSupplier(supplier1);
      productRepository.save(product3);

      // cadastrar clientes
      Customer customer1 = new Customer();
      customer1.setName("João da Silva");
      customer1.setCpf("123.456.789-01");
      customer1.setPhone("(11) 98765-4321");
      customer1.setEmail("joao.silva@exemplo.com");
      customerRepository.save(customer1);

      Customer customer2 = new Customer();
      customer2.setName("Maria da Silva");
      customer2.setCpf("987.654.321-09");
      customer2.setPhone("(11) 91234-5678");
      customer2.setEmail("maria.silva@exemplo.com");
      customerRepository.save(customer2);

      // cadastrar funcionários
      Employee employee1 = new Employee();
      employee1.setName("Roberto Santos");
      employee1.setRole("Gerente");
      employee1.setEmail("roberto.santos@loja.com");
      employee1.setHireDate(LocalDate.of(2020, 1, 15));
      employeeRepository.save(employee1);

      Employee employee2 = new Employee();
      employee2.setName("Sara Almeida");
      employee2.setRole("Vendas");
      employee2.setEmail("sara.almeida@loja.com");
      employee2.setHireDate(LocalDate.of(2021, 3, 10));
      employeeRepository.save(employee2);

      // cadastrar vendas
      Sale sale1 = new Sale();
      sale1.setCustomer(customer1);
      sale1.setEmployee(employee1);
      sale1.setSaleDate(LocalDateTime.now().minusDays(5));
      sale1.setDiscount(new BigDecimal("10.00"));
      sale1.setPaymentMethod(PaymentMethod.CREDIT_CARD);
      saleRepository.save(sale1);

      Sale sale2 = new Sale();
      sale2.setCustomer(customer2);
      sale2.setEmployee(employee2);
      sale2.setSaleDate(LocalDateTime.now().minusDays(2));
      sale2.setDiscount(new BigDecimal("5.00"));
      sale2.setPaymentMethod(PaymentMethod.PIX);
      saleRepository.save(sale2);

      // cadastrar itens de venda
      SaleItem saleItem1 = new SaleItem();
      saleItem1.setSale(sale1);
      saleItem1.setProduct(product1);
      saleItem1.setQuantity(1);
      saleItem1.setUnitPrice(product1.getPrice());
      saleItemRepository.save(saleItem1);

      SaleItem saleItem2 = new SaleItem();
      saleItem2.setSale(sale1);
      saleItem2.setProduct(product2);
      saleItem2.setQuantity(1);
      saleItem2.setUnitPrice(product2.getPrice());
      saleItemRepository.save(saleItem2);

      SaleItem saleItem3 = new SaleItem();
      saleItem3.setSale(sale2);
      saleItem3.setProduct(product3);
      saleItem3.setQuantity(1);
      saleItem3.setUnitPrice(product3.getPrice());
      saleItemRepository.save(saleItem3);

      // atualizar vendas com os itens
      List<SaleItem> items1 = new ArrayList<>();
      items1.add(saleItem1);
      items1.add(saleItem2);
      sale1.setItems(items1);
      saleRepository.save(sale1);

      List<SaleItem> items2 = new ArrayList<>();
      items2.add(saleItem3);
      sale2.setItems(items2);
      saleRepository.save(sale2);

      System.out.println("Sample data has been loaded into the database successfully!");
      System.out.println(getServerSuccessfullyStartedMessage());
    };
  }
}
