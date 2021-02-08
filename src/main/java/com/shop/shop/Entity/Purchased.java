package com.shop.shop.Entity;

public class Purchased {

    private Product product;
    private int purchased;

    public Purchased(Product product, int purchased) {
        this.product = product;
        this.purchased = purchased;
    }

    public Purchased() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }
}
