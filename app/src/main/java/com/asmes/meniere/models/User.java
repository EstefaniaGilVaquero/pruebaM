package com.asmes.meniere.models;

/**
 * Created by Juanma on 27/11/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("pass")
    @Expose
    private String pass;

    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}