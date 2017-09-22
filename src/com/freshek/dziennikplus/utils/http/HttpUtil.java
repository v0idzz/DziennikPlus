package com.freshek.dziennikplus.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Freshek on 18.09.17.
 *
 * A simple Http class to send
 * requests and parse simple
 * stuff
 */
public class HttpUtil {
    private HttpClient httpClient;

    public HttpUtil() {
        this.httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    /**
     * Sends a POST request
     * @param url url to send the request
     * @param params parameters to send with
     *               the request
     * @return the result of the request as a String
     */
    public String post(String url, List<BasicNameValuePair> params) {
        try {
            System.out.println("Executing POST to: " + url);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");

            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sends a GET request
     * @param url url to send the request
     * @return the result of the request as a String
     */
    public String get(String url) {
        try {
            System.out.println("Executing GET to: " + url);

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");

            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Parses a HTML form
     * @param htmlForm HTML code of the form
     * @return parsed form
     * @throws InvalidParameterException in case
     * of invalid parameter (i.e. not containing
     * a form)
     */
    public static Form parseForm(String htmlForm) throws InvalidParameterException {
        String action = "";

        Document document = Jsoup.parseBodyFragment(htmlForm);
        List<Element> formElements = document.getElementsByTag("form");
        Element form;

        if (formElements.size() == 0 || formElements.size() > 1) {
            throw new InvalidParameterException("Invaild form (bad size: " + formElements.size() + ")\nform: " + htmlForm);
        } else {
            form = formElements.get(0);
        }

        if (form.hasAttr("action")) {
            action = form.attr("action");
        }

        List<BasicNameValuePair> pEl = new ArrayList<>();

        for (Element e : form.getElementsByTag("input")) {
            if (e.hasAttr("name")) {
                if (e.hasAttr("value")) {
                    pEl.add(new BasicNameValuePair(e.attr("name"), e.attr("value")));
                } else {
                    pEl.add(new BasicNameValuePair(e.attr("name"), ""));
                }
            }
        }

        return new Form(action, pEl);
    }
}
