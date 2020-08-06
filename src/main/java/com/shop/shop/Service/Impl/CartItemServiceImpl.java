package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Entity.CartItem;
import com.shop.shop.Repositories.CartItemRepository;
import com.shop.shop.Service.Interface.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void addCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void removeAllCartItems(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();

        for(CartItem item : cartItems){
            cartItemRepository.delete(item);
        }

    }

    @Override
    public CartItem getCartItemByProductId(int productId) {
        return null;
    }
}
