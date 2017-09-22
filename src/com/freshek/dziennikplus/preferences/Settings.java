package com.freshek.dziennikplus.preferences;

import com.google.gson.Gson;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Freshek on 22.09.17.
 */
public class Settings {
    private String email;
    private String password;
    private String clientId;

    public Settings(String email, String password, String clientId) {
        this.email = email;
        this.password = password;
        this.clientId = clientId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public void setClientId(String value) {
        this.clientId = value;
    }

    public void save() {
        try {
            PrintWriter writer = new PrintWriter("settings.cfg");
            Gson gson = new Gson();
            writer.write(gson.toJson(this));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
