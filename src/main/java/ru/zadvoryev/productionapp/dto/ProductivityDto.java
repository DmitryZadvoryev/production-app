package ru.zadvoryev.productionapp.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ProductivityDto implements Serializable {

    private LocalDate date;

    private float avgQuantityPerHour;

    public ProductivityDto() {
    }

    public ProductivityDto(LocalDate date, float avgQuantityPerHour) {
        this.date = date;
        this.avgQuantityPerHour = avgQuantityPerHour;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getAvgQuantityPerHour() {
        return avgQuantityPerHour;
    }

    public void setAvgQuantityPerHour(float avgQuantityPerHour) {
        this.avgQuantityPerHour = avgQuantityPerHour;
    }

    @Override
    public String toString() {
        return "ProductivityDTO{" +
                "date=" + date +
                ", avgQuantityPerHour=" + avgQuantityPerHour +
                '}';
    }
}
