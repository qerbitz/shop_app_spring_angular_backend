package com.shop.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Data
@Setter

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_product;

    private String name;
    private String gender;
    private Double price;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            joinColumns = { @JoinColumn(name = "product_id")},
            inverseJoinColumns = { @JoinColumn(name = "category_id")}
    )
    private List<Category> categories = new ArrayList<>();

    private String image;
    private String season;
    private Double discount;


    @OneToMany(mappedBy = "product", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Product_Size> product_sizes = new ArrayList<>();

    public void add(Product_Size item) {

        if (item != null) {
            if (product_sizes == null) {
                product_sizes = new ArrayList<>();
            }

            product_sizes.add(item);
            item.setProduct(this);
        }
    }

}
