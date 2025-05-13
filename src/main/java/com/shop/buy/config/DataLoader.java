package com.shop.buy.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shop.buy.model.Brand;
import com.shop.buy.model.Customer;
import com.shop.buy.model.Employee;
import com.shop.buy.model.Product;
import com.shop.buy.model.Sale;
import com.shop.buy.model.SaleItem;
import com.shop.buy.model.Supplier;
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
            com.shop.buy.model.Category shirts = new com.shop.buy.model.Category();
            shirts.setName("Shirts");
            shirts.setDescription("All types of shirts and t-shirts");
            categoryRepository.save(shirts);
            
            com.shop.buy.model.Category pants = new com.shop.buy.model.Category();
            pants.setName("Pants");
            pants.setDescription("Jeans, trousers, and other leg wear");
            categoryRepository.save(pants);
            
            com.shop.buy.model.Category shoes = new com.shop.buy.model.Category();
            shoes.setName("Shoes");
            shoes.setDescription("Footwear of all types");
            categoryRepository.save(shoes);
    
            // Load products
            Product product1 = new Product();
            product1.setName("Sport T-Shirt");
            product1.setSize("M");
            product1.setColor("Blue");
            product1.setPrice(new java.math.BigDecimal("29.99"));
            product1.setCategory(shirts);
            product1.setBrand(nike);
            productRepository.save(product1);
            
            Product product2 = new Product();
            product2.setName("Running Shoes");
            product2.setSize("42");
            product2.setColor("Black");
            product2.setPrice(new java.math.BigDecimal("89.99"));
            product2.setCategory(shoes);
            product2.setBrand(adidas);
            productRepository.save(product2);
            
            Product product3 = new Product();
            product3.setName("Jeans 501");
            product3.setSize("32");
            product3.setColor("Blue");
            product3.setPrice(new java.math.BigDecimal("79.99"));
            product3.setCategory(pants);
            product3.setBrand(levis);
            productRepository.save(product3);
    
            // Load customers
            Customer customer1 = new Customer();
            customer1.setName("John Doe");
            customer1.setCpf("12345678901");
            customer1.setPhone("(11) 98765-4321");
            customer1.setEmail("john.doe@example.com");
            customerRepository.save(customer1);
            
            Customer customer2 = new Customer();
            customer2.setName("Jane Smith");
            customer2.setCpf("98765432109");
            customer2.setPhone("(11) 91234-5678");
            customer2.setEmail("jane.smith@example.com");
            customerRepository.save(customer2);
    
            // Load employees
            Employee employee1 = new Employee();
            employee1.setName("Robert Johnson");
            employee1.setRole("Manager");
            employee1.setEmail("robert.johnson@clothing.com");
            employee1.setHireDate(java.time.LocalDate.of(2020, 1, 15));
            employeeRepository.save(employee1);
            
            Employee employee2 = new Employee();
            employee2.setName("Sarah Williams");
            employee2.setRole("Sales Associate");
            employee2.setEmail("sarah.williams@clothing.com");
            employee2.setHireDate(java.time.LocalDate.of(2021, 3, 10));
            employeeRepository.save(employee2);
    
            // Load suppliers
            Supplier supplier1 = new Supplier();
            supplier1.setName("Fabric World");
            supplier1.setCnpj("12345678901234");
            supplier1.setPhone("(11) 3333-4444");
            supplier1.setEmail("contact@fabricworld.com");
            supplierRepository.save(supplier1);
            
            Supplier supplier2 = new Supplier();
            supplier2.setName("Textile Solutions");
            supplier2.setCnpj("56789012345678");
            supplier2.setPhone("(11) 5555-6666");
            supplier2.setEmail("info@textilesolutions.com");
            supplierRepository.save(supplier2);
    
            // Create a sale
            Sale sale1 = new Sale();
            sale1.setCustomer(customer1);
            sale1.setSaleDate(java.time.LocalDateTime.now());
            sale1.setTotalValue(new java.math.BigDecimal("119.98"));
            saleRepository.save(sale1);
            
            // Add sale items
            SaleItem item1 = new SaleItem();
            item1.setSale(sale1);
            item1.setProduct(product1);
            item1.setQuantity(1);
            item1.setUnitPrice(product1.getPrice());
            saleItemRepository.save(item1);
            
            SaleItem item2 = new SaleItem();
            item2.setSale(sale1);
            item2.setProduct(product2);
            item2.setQuantity(1);
            item2.setUnitPrice(product2.getPrice());
            saleItemRepository.save(item2);
            
            // Update sale with items
            List<SaleItem> items = new ArrayList<>();
            items.add(item1);
            items.add(item2);
            sale1.setItems(items);
            saleRepository.save(sale1);
            
            System.out.println("Sample data has been loaded into the database successfully!");
        };
    }
}
