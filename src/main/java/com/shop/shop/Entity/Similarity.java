package com.shop.shop.Entity;

public class Similarity implements Comparable<Similarity>{

    private Product product;

    private double similarity;

    public Similarity(Product product, double similarity) {
        this.product = product;
        this.similarity = similarity;
    }

    public Similarity() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }



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
