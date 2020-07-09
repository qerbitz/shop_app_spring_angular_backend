package com.shop.shop.Repositories;

import com.shop.shop.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContaining(@Param("name")String name);

    @Query(
            value = "select * from Product where id_category = :category",
            nativeQuery = true)
    List<Product> findById_category(@Param("category")int category);

}
