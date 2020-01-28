package ru.zadvoryev.productionapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class RecordDto implements Serializable {


    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Введите дату!")
    private LocalDate date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Введите время!")
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Введите время!")
    private LocalTime endTime;

    @NotBlank(message = "Заполните поле!")
    private String nameOfOrganization;

    @NotBlank(message = "Заполните поле!")
    private String nameOfProduct;

    @NotBlank(message = "Заполните поле!")
    private String variant;

    private String side;

    //@Min(value = 1, message = "Заполните поле!")
    private int quantity;

    private Long authorId;

    private String name;

    private String surname;

    public RecordDto(Long id,
                     LocalDate date,
                     LocalTime startTime,
                     LocalTime endTime,
                     String nameOfOrganization,
                     String nameOfProduct,
                     String variant,
                     String side,
                     int quantity,
                     long authorId,
                     String name,
                     String surname
    ) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.nameOfOrganization = nameOfOrganization;
        this.nameOfProduct = nameOfProduct;
        this.variant = variant;
        this.side = side;
        this.quantity = quantity;
        this.authorId = authorId;
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getNameOfOrganization() {
        return nameOfOrganization;
    }

    public void setNameOfOrganization(String nameOfOrganization) {
        this.nameOfOrganization = nameOfOrganization;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "RecordDto{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", nameOfOrganization='" + nameOfOrganization + '\'' +
                ", nameOfProduct='" + nameOfProduct + '\'' +
                ", variant='" + variant + '\'' +
                ", side='" + side + '\'' +
                ", quantity=" + quantity +
                ", authorId=" + authorId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
