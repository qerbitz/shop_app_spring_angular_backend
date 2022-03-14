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

    //Wyszukanie najpopularniejszych produktow(Najwiecej sprzedanych) od najpopularniejszego
    @Query(
            value= "select p.id_product, count(*) as counter \n" +
                    "from orders o, cart_item ct, cart c, product p\n" +
                    "where o.id_cart=c.id_cart \n" +
                    "and c.id_cart=ct.id_cart \n" +
                    "and p.id_product=ct.id_product\n" +
                    "and month(o.order_date) = :month \n" +
                    "group by p.id_product, o.order_date\n" +
                    "order by counter desc",
            nativeQuery = true
    )
    List<Object[]> findAllBySaleDesc(@Param("month")int month);

    @Query(value="SELECT * FROM product p, size_age sa" +
            " where p.size_age=sa.id_size_age" +
            " and sa.age like :age" +
            " and p.season like :season" +
            " and p.gender like :gender",
            nativeQuery = true)
    List<Product> findAllByAgeAndSeasonContaining(String age, String season, String gender);

    @Query(value="select distinct age from size_age",
            nativeQuery = true)
    List<Object[]> findAllAges();

    List<Product> findAllByGenderContaining(String gender);

    List<Product> findAllBySeasonContaining(String season);

    @Query(value="select distinct season from product" +
           " order by season asc",
            nativeQuery = true)
    List<Object[]> findAllSeasons();

    @Query(value="SELECT distinct size FROM size_age where category=:category_id",
            nativeQuery = true)
    List<Object[]> findAllSizesByCategoryId(int category_id);


    @Query(value="select p.id_product, sa.size" +
            " from  product p, size_age sa" +
            " where p.name = :name" +
            " and sa.id_size_age = p.size_age",
            nativeQuery = true)
    List<Object[]> findAvaliableProductsByName(String name);

    @Query(value="SELECT * FROM product" +
            " where discount>0;",
            nativeQuery = true)
    List<Product> findAllByDiscountAvalaible();

    @Query(value="SELECT age FROM size_age where size = :size",
            nativeQuery = true)
    String getAgeOfSize(String size);
}
