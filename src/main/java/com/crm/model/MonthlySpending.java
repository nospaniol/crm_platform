package com.crm.model;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import lombok.Data;
import java.text.SimpleDateFormat;

@Data
public class MonthlySpending implements Serializable {

    String agency;
    Double tollAmount;
    Date tollDate;
    String month;

    public String getDate() {
        return String.valueOf(this.tollDate.getDate());
    }

    public String getTotal() {
        return String.valueOf(getTwoDecimal(Double.valueOf(this.tollAmount)));
    }

    private Double getTwoDecimal(Double PI) {
        DecimalFormat df = new DecimalFormat("###.##");
        return Double.valueOf(df.format(PI));
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public Double getTollAmount() {
        return tollAmount;
    }

    public void setTollAmount(Double tollAmount) {
        this.tollAmount = tollAmount;
    }

    public Date getTollDate() {
        return tollDate;
    }

    public void setTollDate(Date tollDate) {
        this.tollDate = tollDate;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        //   return "{\"label:\":\"" + agency + "\", \"value:\":\"" + tollAmount + "\", \"tollDate\":\"" + getFormattedDate(tollDate) + "\", \"month\":\"" + month + "\"}";
        return "{y:\"" + month + "\", a:\"" + getTwoDecimal(tollAmount) + "\"}";
    }

    private String getFormattedDate(Date todate) {
        if (todate == null) {
            todate = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(todate);
        return strDate;
    }

    public int getMonthVal() {
        return getMonthInt(this.month);
    }

    private int getMonthInt(String month) {
        int digit = 0;
        switch (month) {
            case "JANUARY":
                digit = 1;
                break;
            case "FEBRUARY":
                digit = 2;
                break;
            case "MARCH":
                digit = 3;
                break;
            case "APRIL":
                digit = 4;
                break;
            case "MAY":
                digit = 5;
                break;
            case "JUNE":
                digit = 6;
                break;
            case "JULY":
                digit = 7;
                break;
            case "AUGUST":
                digit = 8;
                break;
            case "SEPTEMBER":
                digit = 9;
                break;
            case "OCTOBER":
                digit = 10;
                break;
            case "NOVEMBER":
                digit = 11;
                break;
            case "DECEMBER":
                digit = 12;
                break;
        }
        return digit;
    }

}
