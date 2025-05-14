package com.shop.buy.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shop.buy.model.*;
import com.shop.buy.repository.*;

@Configuration
public class DataLoader {
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
                return;
            }
            
            // Load brands
            Brand nike = new Brand();
            nike.setName("Nike");
            nike.setCountry("USA");
            nike.setDescription("Sports and athletic wear");
            brandRepository.save(nike);
            
            Brand adidas = new Brand();
            adidas.setName("Adidas");
            adidas.setCountry("Germany");
            adidas.setDescription("Sports and casual wear");
            brandRepository.save(adidas);
            
            Brand levis = new Brand();
            levis.setName("Levi's");
            levis.setCountry("USA");
            levis.setDescription("Jeans and casual wear");
            brandRepository.save(levis);
            
            // Create categories
            Category shirts = new Category();
            shirts.setName("Shirts");
            shirts.setDescription("All types of shirts and t-shirts");
            categoryRepository.save(shirts);
            
            Category pants = new Category();
            pants.setName("Pants");
            pants.setDescription("Jeans, trousers, and other leg wear");
            categoryRepository.save(pants);
            
            Category shoes = new Category();
            shoes.setName("Shoes");
            shoes.setDescription("Footwear of all types");
            categoryRepository.save(shoes);
    
            // Load products
            Product product1 = new Product();
            product1.setName("Sport T-Shirt");
            product1.setSize("M");
            product1.setColor("Blue");
            product1.setPrice(new BigDecimal("29.99"));
            product1.setCategory(shirts);
            product1.setBrand(nike);
            productRepository.save(product1);
            
            Product product2 = new Product();
            product2.setName("Running Shoes");
            product2.setSize("42");
            product2.setColor("Black");
            product2.setPrice(new BigDecimal("89.99"));
            product2.setCategory(shoes);
            product2.setBrand(adidas);
            productRepository.save(product2);
            
            Product product3 = new Product();
            product3.setName("Jeans 501");
            product3.setSize("32");
            product3.setColor("Blue");
            product3.setPrice(new BigDecimal("79.99"));
            product3.setCategory(pants);
            product3.setBrand(levis);
            productRepository.save(product3);
    
            // Load customers
            Customer customer1 = new Customer();
            customer1.setName("John Doe");
            customer1.setCpf("123.456.789-01");
            customer1.setPhone("(11) 98765-4321");
            customer1.setEmail("john.doe@example.com");
            customerRepository.save(customer1);
            
            Customer customer2 = new Customer();
            customer2.setName("Jane Smith");
            customer2.setCpf("987.654.321-09");
            customer2.setPhone("(11) 91234-5678");
            customer2.setEmail("jane.smith@example.com");
            customerRepository.save(customer2);
            
            // Load employees
            Employee employee1 = new Employee();
            employee1.setName("Robert Johnson");
            employee1.setRole("Manager");
            employee1.setEmail("robert.johnson@clothing.com");
            employee1.setHireDate(LocalDate.of(2020, 1, 15));
            employeeRepository.save(employee1);
            
            Employee employee2 = new Employee();
            employee2.setName("Sarah Williams");
            employee2.setRole("Sales Associate");
            employee2.setEmail("sarah.williams@clothing.com");
            employee2.setHireDate(LocalDate.of(2021, 3, 10));
            employeeRepository.save(employee2);
    
            // Load suppliers
            Supplier supplier1 = new Supplier();
            supplier1.setName("Fabric World");
            supplier1.setCnpj("12.345.678/0001-90");
            supplier1.setPhone("(11) 3333-4444");
            supplier1.setEmail("contact@fabricworld.com");
            supplierRepository.save(supplier1);
            
            Supplier supplier2 = new Supplier();
            supplier2.setName("Textile Solutions");
            supplier2.setCnpj("98.765.432/0001-10");
            supplier2.setPhone("(11) 5555-6666");
            supplier2.setEmail("info@textilesolutions.com");
            supplierRepository.save(supplier2);
    
            // Create sales
            Sale sale1 = new Sale();
            sale1.setCustomer(customer1);
            sale1.setSaleDate(LocalDateTime.now().minusDays(5));
            sale1.setTotalValue(new BigDecimal("119.98"));
            saleRepository.save(sale1);
            
            Sale sale2 = new Sale();
            sale2.setCustomer(customer2);
            sale2.setSaleDate(LocalDateTime.now().minusDays(2));
            sale2.setTotalValue(new BigDecimal("79.99"));
            saleRepository.save(sale2);
            
            // Create sale items
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
            
            // Update sale with items
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
        };
    }
}
