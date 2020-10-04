package com.shop.shop.Repositories;

import com.shop.shop.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {


    @Query(value = "select sum(ct.quantity) from cart c, cart_item ct" +
            " where c.id_cart = :cartId" +
            " and ct.id_cart = c.id_cart",
            nativeQuery = true)
    int getQuantityofCart(int cartId);


    @Query(value = "select sum(total_price) from cart c, cart_item ct" +
            " where c.id_cart = :cartId" +
            " and ct.id_cart = c.id_cart",
            nativeQuery = true)
    Double getTotalPrice(int cartId);
}
