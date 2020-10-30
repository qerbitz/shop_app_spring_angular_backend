package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Repositories.CartRepository;
import com.shop.shop.Service.Interface.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

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
        Integer quantity = cartRepository.getQuantityofCart(cartId);
        if(quantity == null){
            return 0;
        }
        else
        {
            return quantity;
        }
    }

    @Override
    public String getTotalPrice(int cartId) {

        if(cartRepository.getTotalPrice(cartId)==null)
        {
            return "0 zł";
        }else
        {
            String total = new DecimalFormat("#0.00").format(cartRepository.getTotalPrice(cartId))+" zł";
            return total;
        }
    }
}
