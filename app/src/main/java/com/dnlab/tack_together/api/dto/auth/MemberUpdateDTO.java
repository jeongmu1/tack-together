package com.dnlab.tack_together.api.dto.auth;

import java.io.Serializable;

public class MemberUpdateDTO implements Serializable {
    private String nickname;
    private String name;
    private String password;

    public MemberUpdateDTO(){};

    public MemberUpdateDTO(String nickname, String name, String password){
        this.nickname = nickname;
        this.name = name;
        this.password = password;
    };

    @Override
    public String toString() {
        return "MemberUpdateDTO{" +
                "nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
