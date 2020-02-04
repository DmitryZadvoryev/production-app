package ru.zadvoryev.productionapp.dto;


import java.io.Serializable;

public class DistinctProductDto implements Serializable {

    private String nameOfProduct;

    private String variant;

    private String side;

    public DistinctProductDto(String nameOfProduct, String variant, String side) {
        this.nameOfProduct = nameOfProduct;
        this.variant = variant;
        this.side = side;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "DistinctProductDTO{" +
                "nameOfProduct='" + nameOfProduct + '\'' +
                ", variant='" + variant + '\'' +
                ", side='" + side + '\'' +
                '}';
    }
}
