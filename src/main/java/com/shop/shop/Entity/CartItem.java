package com.shop.shop.Entity;


import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @Column(name = "id_cart_item")
    private int id_cart_item;

    @ManyToOne
    @JoinColumn(name = "id_cart")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product", nullable = false, //
            foreignKey = @ForeignKey(name = "ORDER_DETAIL_PROD_FK"))
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_price", nullable = false)
    private double total_price;

    public int getId_order_detail() {
        return id_cart_item;
    }

    public void setId_order_detail(int id_order_detail) {
        this.id_cart_item = id_order_detail;
    }

    public int getId_cart_item() {
        return id_cart_item;
    }

    public void setId_cart_item(int id_cart_item) {
        this.id_cart_item = id_cart_item;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quanity) {
        this.quantity = quanity;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

}
