package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Entity.CartItem;

public interface CartItemService {

    void addCartItem(CartItem cartItem);
    void removeCartItem(CartItem cartItem);
    void removeAllCartItems(Cart cart);
    CartItem getCartItemByProductId(int productId);
}
