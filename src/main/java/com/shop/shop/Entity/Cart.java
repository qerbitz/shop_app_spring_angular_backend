package com.shop.shop.Entity;



import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart")
    private int id_cart;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;


    private double grandTotal;

    public int getId_cart() {
        return id_cart;
    }

    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }



    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id_cart=" + id_cart +
                ", cartItems=" + cartItems +
                ", grandTotal=" + grandTotal +
                '}';
    }
}
