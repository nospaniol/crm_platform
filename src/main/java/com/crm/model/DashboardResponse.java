package com.crm.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class DashboardResponse implements Serializable {

    Long vehicleCount;
    Long clientCount;
    Long transactionCount;
    Long disputedTransactionCount;
    Long speedTicketCount;
    Double speedTicketAmount;
    Double totalSavings;
    Long parkingCount;
    Double parkingAmount;
    Long redLightCount;
    Double redLightAmount;
    List<Tollspendings> tollSpendings;
    Double spendingAmount;
    Double transactionTotal;
    String title;
    String message;
    List<DailySpending> agencySpendings;
    List<DailySpending> monthlySpendings;
    List<DailySpending> dailiesSpendings;
    List<Transaction> transactions;
    List<Vehicle> vehicles;

    @Override
    public String toString() {
        return "{\"vehicleCount\":" + vehicleCount + ", \"totalSavings\":" + totalSavings + " , \"clientCount\":" + clientCount + ", \"transactionCount\":" + transactionCount + ", \"disputedTransactionCount\":" + disputedTransactionCount + ", \"speedTicketCount\":" + speedTicketCount + ", \"speedTicketAmount\":" + speedTicketAmount + ", \"parkingCount\":" + parkingCount + ", \"parkingAmount\":" + parkingAmount + ", \"redLightCount\":" + redLightCount + ", \"redLightAmount\":" + redLightAmount + ", \"tollSpendings\":" + tollSpendings + ", \"spendingAmount\":" + spendingAmount + ", \"title\":\"" + title + "\", \"message\":\"" + message + "\", \"agencySpendings\":" + agencySpendings + ", \"monthlySpendings\":" + monthlySpendings + ", \"dailiesSpendings\":" + dailiesSpendings + ", \"transactions\":" + transactions + ", \"vehicles\":" + vehicles + ", \"transactionTotal\":" + transactionTotal + "}";
    }

    public Long getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(Long vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public Double getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(Double totalSavings) {
        this.totalSavings = totalSavings;
    }

    public Long getClientCount() {
        return clientCount;
    }

    public void setClientCount(Long clientCount) {
        this.clientCount = clientCount;
    }

    public Long getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Long transactionCount) {
        this.transactionCount = transactionCount;
    }

    public Long getDisputedTransactionCount() {
        return disputedTransactionCount;
    }

    public void setDisputedTransactionCount(Long disputedTransactionCount) {
        this.disputedTransactionCount = disputedTransactionCount;
    }

    public Long getSpeedTicketCount() {
        return speedTicketCount;
    }

    public void setSpeedTicketCount(Long speedTicketCount) {
        this.speedTicketCount = speedTicketCount;
    }

    public Double getSpeedTicketAmount() {
        return speedTicketAmount;
    }

    public void setSpeedTicketAmount(Double speedTicketAmount) {
        this.speedTicketAmount = speedTicketAmount;
    }

    public Long getParkingCount() {
        return parkingCount;
    }

    public void setParkingCount(Long parkingCount) {
        this.parkingCount = parkingCount;
    }

    public Double getParkingAmount() {
        return parkingAmount;
    }

    public void setParkingAmount(Double parkingAmount) {
        this.parkingAmount = parkingAmount;
    }

    public Long getRedLightCount() {
        return redLightCount;
    }

    public void setRedLightCount(Long redLightCount) {
        this.redLightCount = redLightCount;
    }

    public Double getRedLightAmount() {
        return redLightAmount;
    }

    public void setRedLightAmount(Double redLightAmount) {
        this.redLightAmount = redLightAmount;
    }

    public List<Tollspendings> getTollSpendings() {
        return tollSpendings;
    }

    public void setTollSpendings(List<Tollspendings> tollSpendings) {
        this.tollSpendings = tollSpendings;
    }

    public Double getSpendingAmount() {
        return spendingAmount;
    }

    public void setSpendingAmount(Double spendingAmount) {
        this.spendingAmount = spendingAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DailySpending> getAgencySpendings() {
        return agencySpendings;
    }

    public void setAgencySpendings(List<DailySpending> agencySpendings) {
        this.agencySpendings = agencySpendings;
    }

    public List<DailySpending> getMonthlySpendings() {
        return monthlySpendings;
    }

    public void setMonthlySpendings(List<DailySpending> monthlySpendings) {
        this.monthlySpendings = monthlySpendings;
    }

    public List<DailySpending> getDailiesSpendings() {
        return dailiesSpendings;
    }

    public void setDailiesSpendings(List<DailySpending> dailiesSpendings) {
        this.dailiesSpendings = dailiesSpendings;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Double getTransactionTotal() {
        return transactionTotal;
    }

    public void setTransactionTotal(Double transactionTotal) {
        this.transactionTotal = transactionTotal;
    }
}
