package com.dnlab.tack_together.api.dto.match;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchRequestDTO implements Serializable {
    @SerializedName("username")
    private String username;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("origin")
    private String origin;
    @SerializedName("destination")
    private String destination;
    @SerializedName("originRange")
    private short originRange;
    @SerializedName("destinationRange")
    private short destinationRange;

    @Override
    public String toString() {
        return "MatchRequestDTO{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", originRange=" + originRange +
                ", destinationRange=" + destinationRange +
                '}';
    }

    public MatchRequestDTO() {
    }

    public MatchRequestDTO(String username, String nickname, String origin, String destination, short originRange, short destinationRange) {
        this.username = username;
        this.nickname = nickname;
        this.origin = origin;
        this.destination = destination;
        this.originRange = originRange;
        this.destinationRange = destinationRange;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public short getOriginRange() {
        return originRange;
    }

    public void setOriginRange(short originRange) {
        this.originRange = originRange;
    }

    public short getDestinationRange() {
        return destinationRange;
    }

    public void setDestinationRange(short destinationRange) {
        this.destinationRange = destinationRange;
    }
}
