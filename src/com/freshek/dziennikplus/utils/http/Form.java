package com.freshek.dziennikplus.utils.http;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

/**
 * Created by Freshek on 19.09.17.
 */
public class Form {
    private String action;
    private List<BasicNameValuePair> values;

    public Form(String action, List<BasicNameValuePair> values) {
        this.action = action;
        this.values = values;
    }

    public String getAction() {
        return action;
    }

    public List<BasicNameValuePair> getValues() {
        return values;
    }
}
