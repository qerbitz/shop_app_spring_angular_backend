package com.shop.shop.Entity;

public class Item {

    private Product product;

    private int Quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Item(Product product, int quantity) {
        this.product = product;
        Quantity = quantity;
    }

    public Item() {
    }
}
