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

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user" ,nullable = false)
    private User id_user;

    public Order(int id_order, Date orderDate, double amount, User id_user) {
        this.id_order = id_order;
        this.orderDate = orderDate;
        this.amount = amount;
        this.id_user = id_user;
    }

    public Order() {
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

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }
}
