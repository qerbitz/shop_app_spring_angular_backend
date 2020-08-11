package com.shop.shop.Repositories;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Entity.CartItem;
import com.shop.shop.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {


    CartItem getCartItemByProductAndCart(Product product, Cart cart);


    @Modifying
    @Query( value = "delete from CartItem where id_cart_item = :id_item")
    void deleteCartItemById_cart_item(@Param("id_item")int id_item);

}
