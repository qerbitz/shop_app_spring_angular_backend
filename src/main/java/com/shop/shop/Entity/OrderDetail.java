package com.shop.shop.Entity;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @Column(name = "id_order_detail")
    private int id_order_detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product", nullable = false, //
            foreignKey = @ForeignKey(name = "ORDER_DETAIL_PROD_FK"))
    private Product product;

    @Column(name = "Quanity", nullable = false)
    private int quanity;

    @Column(name = "Price", nullable = false)
    private double price;

    @Column(name = "Amount", nullable = false)
    private double amount;

    public int getId_order_detail() {
        return id_order_detail;
    }

    public void setId_order_detail(int id_order_detail) {
        this.id_order_detail = id_order_detail;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
