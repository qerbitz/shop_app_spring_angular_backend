package com.shop.shop.Service.Impl;

import com.shop.shop.Entity.Cart;
import com.shop.shop.Entity.CartItem;
import com.shop.shop.Entity.Product;
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
        cartItemRepository.deleteCartItemById_cart_item(cartItem.getId_cart_item());
    }

    @Override
    public void removeAllCartItems(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();

        for(CartItem item : cartItems){
            cartItemRepository.deleteCartItemById_cart_item(item.getId_cart_item());
        }

    }

    @Override
    public CartItem getCartItemByProductId(Product product, Cart cart) {
        return cartItemRepository.getCartItemByProductAndCart(product, cart);
    }
}
