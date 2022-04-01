package com.shop.shop.repositories;

import com.shop.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByNameContaining(Pageable pageable, @Param("name")String name);
    
    Page<Product> findAll(Pageable pageable);

    //Wyszukiwanie wszystkich produktów ze względu na kategorie
    @Query(
            value = "select * from Product where id_category = :category",
            nativeQuery = true)
    Page<Product> findById_category(Pageable pageable, @Param("category")int category);

    //Wyszukanie najpopularniejszych produktow(Najwiecej sprzedanych) od najpopularniejszego
    @Query(
            value= "select p.id_product, sum(oi.quantity) as counter\n" +
                    "from order_item oi, product p\n" +
                    "where oi.id_product = p.id_product\n" +
                    "group by p.id_product\n" +
                    "order by counter desc;",
            nativeQuery = true
    )
    List<Object[]> findAllBySaleDesc();
    

}
