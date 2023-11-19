package com.authentication.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Token
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column
    private String AccessToken;

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

}
