package com.dnlab.tack_together.api.dto.match;

import com.dnlab.tack_together.api.dto.route.RouteDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MatchResultInfoDTO implements Serializable {
    @SerializedName("username")
    private String username;
    @SerializedName("opponentNickname")
    private String opponentNickname;
    @SerializedName("routes")
    private List<RouteDTO> routes;
    @SerializedName("estimatedTotalTaxiFare")
    private int estimatedTotalTaxiFare;
    @SerializedName("estimatedTaxiFare")
    private int estimatedTaxiFare;
    @SerializedName("paymentRate")
    private double paymentRate;
    @SerializedName("opponentPaymentRate")
    private double opponentPaymentRate;

    public MatchResultInfoDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOpponentNickname() {
        return opponentNickname;
    }

    public void setOpponentNickname(String opponentNickname) {
        this.opponentNickname = opponentNickname;
    }

    public List<RouteDTO> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteDTO> routes) {
        this.routes = routes;
    }

    public int getEstimatedTotalTaxiFare() {
        return estimatedTotalTaxiFare;
    }

    public void setEstimatedTotalTaxiFare(int estimatedTotalTaxiFare) {
        this.estimatedTotalTaxiFare = estimatedTotalTaxiFare;
    }

    public int getEstimatedTaxiFare() {
        return estimatedTaxiFare;
    }

    public void setEstimatedTaxiFare(int estimatedTaxiFare) {
        this.estimatedTaxiFare = estimatedTaxiFare;
    }

    public double getPaymentRate() {
        return paymentRate;
    }

    public void setPaymentRate(double paymentRate) {
        this.paymentRate = paymentRate;
    }

    public double getOpponentPaymentRate() {
        return opponentPaymentRate;
    }

    public void setOpponentPaymentRate(double opponentPaymentRate) {
        this.opponentPaymentRate = opponentPaymentRate;
    }

    @Override
    public String toString() {
        return "MatchResultInfoDTO{" +
                "username='" + username + '\'' +
                ", opponentNickname='" + opponentNickname + '\'' +
                ", routes=" + routes +
                ", estimatedTotalTaxiFare=" + estimatedTotalTaxiFare +
                ", estimatedTaxiFare=" + estimatedTaxiFare +
                ", paymentRate=" + paymentRate +
                ", opponentPaymentRate=" + opponentPaymentRate +
                '}';
    }

}
