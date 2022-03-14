package com.shop.shop.service.Interface;

import com.shop.shop.entity.Purchase;
import com.shop.shop.response.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}