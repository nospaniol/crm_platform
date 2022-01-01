package com.crm.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Comparator;
import lombok.Data;

@Data
public class Tollspendings implements Serializable, Comparable<Tollspendings> {

    private static final long serialVersionUID = 1L;

    private Vehicle vehicle;
    private Double total;
    private String plate;
    private String transactionMonth;
    private int transactionYear;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getTransactionMonth() {
        return transactionMonth;
    }

    public void setTransactionMonth(String transactionMonth) {
        this.transactionMonth = transactionMonth;
    }

    public int getTransactionYear() {
        return transactionYear;
    }

    public void setTransactionYear(int transactionYear) {
        this.transactionYear = transactionYear;
    }

    public Double getTollTotal() {
        return getTwoDecimal(this.total);
    }

    private Double getTwoDecimal(Double PI) {
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.valueOf(df.format(PI));
    }

    @Override
    public String toString() {
        return "{\"vehicle\":" + vehicle + ", \"total\":" + getTwoDecimal(total) + ", \"plate\":\"" + plate + "\",\"state\":\"" +getState() +"\",\"tagType\":\"" +getTagType() +"\",\"axle\":\"" + getAxle()+ "\", \"transactionMonth\":\"" + transactionMonth + "\", \"transactionYear\":" + transactionYear + "}";
    }

    private String getTagType() {
        if (this.vehicle.getTagType() != null) {
            return this.vehicle.getTagType().getTagTypeName();
        }
        return "";
    }
    
    private String getAxle() {
        if (this.vehicle.getAxle() != null) {
            return this.vehicle.getAxle().getAxleName();
        }
        return "";
    }

       private String getState() {
        if (this.vehicle.getState()!= null) {
            return this.vehicle.getState();
        }
        return "";
    }
    
    @Override
    public int compareTo(Tollspendings comparestu) {
        int compareage = (int) Math.round(((Tollspendings) comparestu).getTotal());
        int origi = (int) Math.round(this.total);
        return origi - compareage;
    }

}
