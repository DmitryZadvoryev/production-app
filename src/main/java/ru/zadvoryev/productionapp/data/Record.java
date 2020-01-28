package ru.zadvoryev.productionapp.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@SuppressWarnings("PMD")
@Entity
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date", nullable = false)
    @NotNull(message = "Введите дату!")
    private LocalDate date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Column(name = "start_time", nullable = false)
    @NotNull(message = "Введите время!")
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "Введите время!")
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @NotBlank(message = "Заполните поле!")
    @Column(name = "name_of_organization", nullable = false)
    private String nameOfOrganization;

    @NotBlank(message = "Заполните поле!")
    @Column(name = "name_of_product", nullable = false)
    private String nameOfProduct;

    @NotBlank(message = "Заполните поле!")
    @Column(name = "variant", nullable = false)
    private String variant;

    @Column(name = "side", nullable = false)
    private String side;


    @Min(value = 1, message = "Заполните поле!")
    @Column(name = "quantity")
    private int quantity ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id", nullable = false)
    Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public Record() {
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

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return quantity == record.quantity &&
                Objects.equals(id, record.id) &&
                Objects.equals(date, record.date) &&
                Objects.equals(startTime, record.startTime) &&
                Objects.equals(endTime, record.endTime) &&
                Objects.equals(nameOfOrganization, record.nameOfOrganization) &&
                Objects.equals(nameOfProduct, record.nameOfProduct) &&
                Objects.equals(variant, record.variant) &&
                Objects.equals(side, record.side) &&
                Objects.equals(line, record.line) &&
                Objects.equals(author, record.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, startTime, endTime, nameOfOrganization, nameOfProduct, variant, side, quantity, line, author);
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", nameOfOrganization='" + nameOfOrganization + '\'' +
                ", nameOfProduct='" + nameOfProduct + '\'' +
                ", variant='" + variant + '\'' +
                ", side='" + side + '\'' +
                ", quantity=" + quantity +
                ", author=" + author +
                '}';
    }

}
