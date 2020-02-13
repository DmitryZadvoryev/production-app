package ru.zadvoryev.productionapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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

    @Min(value = 1, message = "Заполните поле!")
    private int quantity;

    private LineDto line;

    private UserDto author;


    public RecordDto() {
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

    public LineDto getLine() {
        return line;
    }

    public void setLine(LineDto line) {
        this.line = line;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordDto recordDto = (RecordDto) o;
        return quantity == recordDto.quantity &&
                Objects.equals(id, recordDto.id) &&
                Objects.equals(date, recordDto.date) &&
                Objects.equals(startTime, recordDto.startTime) &&
                Objects.equals(endTime, recordDto.endTime) &&
                Objects.equals(nameOfOrganization, recordDto.nameOfOrganization) &&
                Objects.equals(nameOfProduct, recordDto.nameOfProduct) &&
                Objects.equals(variant, recordDto.variant) &&
                Objects.equals(side, recordDto.side) &&
                Objects.equals(line, recordDto.line) &&
                Objects.equals(author, recordDto.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, startTime, endTime, nameOfOrganization, nameOfProduct, variant, side, quantity, line, author);
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
                ", line=" + line +
                ", author=" + author +
                '}';
    }
}
