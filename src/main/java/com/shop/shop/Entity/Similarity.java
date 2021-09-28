package com.shop.shop.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Similarity implements Comparable<Similarity>{

    private Product product;

    private double similarity;


    @Override
    public int compareTo(Similarity o) {
        if(getSimilarity()<o.getSimilarity()){
            return 1;
        }
        else if(getSimilarity()>o.getSimilarity()){
            return -1;
        }
        return 0;
    }
}
