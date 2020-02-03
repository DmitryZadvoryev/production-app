package ru.zadvoryev.productionapp.dto;

import ru.zadvoryev.productionapp.data.Record;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

public class LineDto implements Serializable {

    private Long id;

    @NotBlank(message = "Заполните поле!")
    private String name;

    List<Record> records;

    public LineDto(Long id, String name, List<Record> records) {
        this.id = id;
        this.name = name;
        this.records = records;
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

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "LineDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
