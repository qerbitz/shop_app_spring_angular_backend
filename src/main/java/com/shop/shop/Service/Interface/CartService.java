package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Entity.User;

public interface CartService {

    void addCart(Cart cart);

    Cart getCartById(int cartId);
}
