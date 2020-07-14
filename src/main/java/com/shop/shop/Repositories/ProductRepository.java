package com.shop.shop.Repositories;

import com.shop.shop.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    //Wyszukiwanie wszystkich produktów po nazwie
    List<Product> findByNameContaining(@Param("name")String name);

    //Wyszukiwanie wszystkich produktów ze względu na kategorie
    @Query(
            value = "select * from Product where id_category = :category",
            nativeQuery = true)
    List<Product> findById_category(@Param("category")int category);

    //Wyszukiwanie wszystkich produktów po nazwe rosnąco
    List<Product> findAllByOrderByNameAsc();

    //Wyszukiwanie wszystkich produktów po nazwe malejąca
    List<Product> findAllByOrderByNameDesc();

    //Wyszukiwanie wszystkich produktów po cenie rosnąco
    List<Product> findAllByOrderByPriceAsc();

    //Wyszukiwanie wszystkich produktów po cenie malejąca
    List<Product> findAllByOrderByPriceDesc();

    //Wyszukanie wszystkich produktów w podanym przedziale
    @Query(
            value = "select * from Product where price>= :price_min and price <= :price_max",
            nativeQuery = true
    )
    List<Product> findAllByPriceBetween(@Param("price_min")int price_min, @Param("price_max")int price_max);

}
