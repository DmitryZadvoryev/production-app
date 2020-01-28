package ru.zadvoryev.productionapp.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class LineDto implements Serializable {

    private Long id;

    @NotBlank(message = "Заполните поле!")
    private String name;

    public LineDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LineDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
