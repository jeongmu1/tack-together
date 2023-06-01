package com.dnlab.tack_together.api.dto.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistorySummaryDTO implements Serializable {

    @SerializedName("id")
    private long id;
    @SerializedName("date")
    private long date;
    @SerializedName("origin")
    private String origin;
    @SerializedName("destination")
    private String destination;
    @SerializedName("paymentAmount")
    private int paymentAmount;

    @Override
    public String toString() {
        return "HistorySummaryDTO{" +
                "id=" + id +
                ", date=" + date +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", paymentAmount=" + paymentAmount +
                '}';
    }

    public HistorySummaryDTO(long id, long date, String origin, String destination, int paymentAmount) {
        this.id = id;
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.paymentAmount = paymentAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
