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
@Table(name="product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int id_product;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    @Column(name = "image")
    private String image;

    @Column(name = "season")
    private String season;

    @Column(name = "discount")
    private Double discount;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
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
