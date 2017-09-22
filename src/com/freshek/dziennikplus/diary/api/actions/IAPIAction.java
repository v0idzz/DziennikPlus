package com.freshek.dziennikplus.diary.api.actions;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by Freshek on 18.09.17.
 */
public interface IAPIAction {
    String getEndpoint();
    Object parseResult(String result);
}
