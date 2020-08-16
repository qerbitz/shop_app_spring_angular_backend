package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Cart;

public interface CartService {

    void addCart(Cart cart);

    Cart getCartById(int cartId);

    int getQuantityofCart(int cartId);

    Double getTotalPrice(int cartId);
}
