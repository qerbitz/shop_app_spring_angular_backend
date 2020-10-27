package com.shop.shop.Entity;


import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart_item")
    private int id_cart_item;

    @ManyToOne
    @JoinColumn(name = "id_cart")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private double total_price;

    public CartItem() {
    }

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

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
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
