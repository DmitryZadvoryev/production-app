package ru.zadvoryev.productionapp.dto;

import ru.zadvoryev.productionapp.data.Record;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineDto)) return false;
        LineDto lineDto = (LineDto) o;
        return Objects.equals(getId(), lineDto.getId()) &&
                Objects.equals(getName(), lineDto.getName()) &&
                Objects.equals(getRecords(), lineDto.getRecords());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getRecords());
    }

    @Override
    public String toString() {
        return "LineDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
