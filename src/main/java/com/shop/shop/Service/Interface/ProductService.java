package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Producent;
import com.shop.shop.Entity.Product;
import com.shop.shop.Entity.Purchased;
import com.shop.shop.Entity.Size_Age;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ProductService {

    List<Product> getListofAvaliableProductsByName(String name);

    List<String> getListOfSizesBy(int category_id);

    String getAgeOfSize(String size);

    List<String> getListOfGenders();

    List<Product> getListOfProducts();

    List<Producent> getListOfProducents();

    List<Product> getListOfProductsByName(String name);

    List<Product> getListOfProductsByCategory(int category);

    List<Product> getListOfProductsOrderByNameAsc();

    List<Product> getListOfProductsOrderByNameDesc();

    List<Product> getListOfProductOrderByPriceAsc();

    List<Product> getListOfProductOrderByPriceDesc();

    List<Purchased> getListOfProductsOrderBySaleDesc(int month);

    List<Product> getListOfProductByPriceBetween(int price_min, int price_max);

    List<Product> getListOfProductsByAgeContaining(int nowy_poczatek, int nowy_koniec, List<Product> proponowane, String season, String gender);

    List<Product> getListOfProductsWithDiscount();

    List<String> getListOfAges();

    List<String> getListOfSeasons();

    List<Size_Age> getListOfSizeAges();

    Page<Product> listAll(int pageNum, int pageSize);

    Product getProductById(int id);

    Producent getProducentById(int id);

    boolean changeQuantityOfProduct(Product product, int quantity);


}
