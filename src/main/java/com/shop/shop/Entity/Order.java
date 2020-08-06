package com.shop.shop.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @Column(name = "id_order")
    private int id_order;

    @Column(name = "order_Date", nullable = false)
    private Date orderDate;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name= "status")
    private String status;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user" ,nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cart", nullable = false)
    private Cart id_cart;

    public Order(int id_order, Date orderDate, double amount, User user) {
        this.id_order = id_order;
        this.orderDate = orderDate;
        this.amount = amount;
        this.user = user;
    }

    public Order() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cart getId_cart() {
        return id_cart;
    }

    public void setId_cart(Cart id_cart) {
        this.id_cart = id_cart;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
