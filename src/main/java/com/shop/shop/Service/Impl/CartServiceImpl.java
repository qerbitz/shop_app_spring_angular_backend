package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Repositories.CartRepository;
import com.shop.shop.Service.Interface.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public void addCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(int cartId) {
        return cartRepository.getOne(cartId);
    }

    @Override
    public int getQuantityofCart(int cartId) {
        return cartRepository.getQuantityofCart(cartId);
    }

    @Override
    public Double getTotalPrice(int cartId) {

        if(cartRepository.getTotalPrice(cartId)==null)
        {
            return 0.0;
        }else
        {
            return cartRepository.getTotalPrice(cartId);
        }
    }
}
