package com.shop.shop.Service.Interface;

import com.shop.shop.Entity.Product;

import java.util.List;

public interface OrderService {

    void addOrder(List<Product> theList);

    void removeElementFromOrder(List<Product>  theListToRemove);


}
