package com.shop.shop.Entity;

import javax.persistence.*;

@Entity
@Table(name="size_age")
public class Size_Age {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_size_age")
    private int id_size_age;

    @Column(name = "size")
    private String product_size;

    @Column(name = "age")
    private String product_age;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category", nullable = false)
    private Category id_category;

    public Size_Age(int id_size_age, String product_size, String product_age, Category id_category) {
        this.id_size_age = id_size_age;
        this.product_size = product_size;
        this.product_age = product_age;
        this.id_category = id_category;
    }

    public Size_Age() {
    }

    public int getId_size_age() {
        return id_size_age;
    }

    public void setId_size_age(int id_size_age) {
        this.id_size_age = id_size_age;
    }

    public String getProduct_size() {
        return product_size;
    }

    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }

    public String getProduct_age() {
        return product_age;
    }

    public void setProduct_age(String product_age) {
        this.product_age = product_age;
    }

    public Category getId_category() {
        return id_category;
    }

    public void setId_category(Category id_category) {
        this.id_category = id_category;
    }
}
