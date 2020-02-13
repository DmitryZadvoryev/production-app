package ru.zadvoryev.productionapp.dto;

import ru.zadvoryev.productionapp.data.Record;

import java.io.Serializable;
import java.util.Objects;

public class ReportForTimeDto implements Serializable {

    private String nameOrg;

    private String manePr;

    private String var;

    private int quantity;

    private String side;

    private Long lineId;

    private String lineName;

    public ReportForTimeDto(Record record) {
        this.nameOrg = record.getNameOfOrganization();
        this.manePr = record.getNameOfProduct();
        this.var = record.getVariant();
        this.quantity = record.getQuantity();
        this.side = record.getSide();
        this.lineId = record.getLine().getId();
        this.lineName = record.getLine().getName();
    }

    public String getNameOrg() {
        return nameOrg;
    }

    public void setNameOrg(String nameOrg) {
        this.nameOrg = nameOrg;
    }

    public String getNamePr() {
        return manePr;
    }

    public void setManePr(String manePr) {
        this.manePr = manePr;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportForTimeDto)) return false;
        ReportForTimeDto that = (ReportForTimeDto) o;
        return getQuantity() == that.getQuantity() &&
                Objects.equals(getNameOrg(), that.getNameOrg()) &&
                Objects.equals(manePr, that.manePr) &&
                Objects.equals(getVar(), that.getVar()) &&
                Objects.equals(getSide(), that.getSide()) &&
                Objects.equals(getLineId(), that.getLineId()) &&
                Objects.equals(getLineName(), that.getLineName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNameOrg(), manePr, getVar(), getQuantity(), getSide(), getLineId(), getLineName());
    }

    @Override
    public String toString() {
        return "RecordDto{" +
                "nameOrg='" + nameOrg + '\'' +
                ", manePr='" + manePr + '\'' +
                ", var='" + var + '\'' +
                ", quantity=" + quantity +
                ", side='" + side + '\'' +
                ", lineId=" + lineId +
                ", lineName='" + lineName + '\'' +
                '}';
    }

}
