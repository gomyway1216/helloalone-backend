package com.yudaiyaguchi.helloalonebackend.models;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.cloud.firestore.annotation.ServerTimestamp;

//import com.google.firebase.firestore.ServerTimestamp;

//import javax.persistence.*;

//@Entity(name = "refreshtoken")
public class RefreshToken {
    // @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // private long id;
    private String id;

    // @OneToOne
    // @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // @Column(nullable = false, unique = true)
    private String token;

    // @Column(nullable = false)
    // private Instant expiryDate;
    // private Timestamp expiryTime;
    // @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private @ServerTimestamp Date expiryTime;

    public RefreshToken() {
    }

    public RefreshToken(String id, User user, String token, Date expiryTime) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.expiryTime = expiryTime;
    }

    // public long getId() {
    // return id;
    // }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryTime() {
        return this.expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }
}