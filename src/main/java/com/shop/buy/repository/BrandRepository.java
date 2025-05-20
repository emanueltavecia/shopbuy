package com.shop.buy.repository;

import com.shop.buy.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query(value = "SELECT * FROM brands", nativeQuery = true)
    List<Brand> findAllBrands();

    @Query(value = "SELECT * FROM brands WHERE id = :id", nativeQuery = true)
    Optional<Brand> findBrandById(@Param("id") Long id);

    @Query(value = "SELECT * FROM brands WHERE name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
    List<Brand> findBrandsByNameContaining(@Param("name") String name);

    @Query(value = "INSERT INTO brands (name, country, description) VALUES (:#{#brand.name}, :#{#brand.country}, :#{#brand.description}) RETURNING *", nativeQuery = true)
    Brand saveBrand(@Param("brand") Brand brand);

    @Query(value = "UPDATE brands SET name = :#{#brand.name}, country = :#{#brand.country}, description = :#{#brand.description} WHERE id = :id RETURNING *", nativeQuery = true)
    Brand updateBrand(@Param("id") Long id, @Param("brand") Brand brand);

    @Query(value = "DELETE FROM brands WHERE id = :id", nativeQuery = true)
    @org.springframework.data.jpa.repository.Modifying
    void deleteBrand(@Param("id") Long id);
}
