package com.dnlab.tack_together.activity_history;

public class HistoryItemActivity {
    private long id;
    private long date;
    private String origin;
    private String destination;
    private int paymentAmount;

    public HistoryItemActivity(long id, long date, String origin, String destination, int paymentAmount) {
        this.id = id;
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.paymentAmount = paymentAmount;
    }

    public long getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }
}
