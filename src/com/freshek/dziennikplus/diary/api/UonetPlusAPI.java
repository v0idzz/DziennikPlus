package com.freshek.dziennikplus.diary.api;

import com.freshek.dziennikplus.diary.api.actions.GetGrades;
import com.freshek.dziennikplus.diary.api.actions.IAPIAction;
import com.freshek.dziennikplus.utils.http.Form;
import com.freshek.dziennikplus.utils.http.HttpUtil;
import com.freshek.dziennikplus.diary.objects.GradesTableRow;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Freshek on 18.09.17.
 *
 * The main class to communicate with
 * Uonet Plus
 */
public class UonetPlusAPI {
    private String username;
    private String password;
    private String clientId;

    private HttpUtil httpUtil;

    private String userId;

    public UonetPlusAPI(String username, String password, String clientId) {
        this.username = username;
        this.password = password;
        this.clientId = clientId;

        this.httpUtil = new HttpUtil();
    }

    /**
     * Logins to Uonet Plus
     * @return result of the login
     */
    public boolean login() {
        try {
            String res = httpUtil.get("https://uonetplus.vulcan.net.pl/" + clientId + "/LoginEndpoint.aspx");
            Form loginForm = HttpUtil.parseForm(res);

            String dataPage = httpUtil.post("https://cufs.vulcan.net.pl" + loginForm.getAction(), new ArrayList<BasicNameValuePair>() {{
                add(new BasicNameValuePair("LoginName", username));
                add(new BasicNameValuePair("Password", password));
            }});

            Form dataForm = httpUtil.parseForm(dataPage);
            String homePage = httpUtil.post("https://uonetplus.vulcan.net.pl/" + clientId + "/LoginEndpoint.aspx", dataForm.getValues());

            Document document = Jsoup.parseBodyFragment(homePage);
            Element element = document.getElementsByClass("panel linkownia pracownik klient").get(0);
            Element idElement = element.getElementsByTag("a").get(0);

            if (!idElement.hasAttr("href"))
                return false;

            String href = idElement.attr("href");

            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(href);

            if (!matcher.find())
                return false;

            userId = matcher.group();
            httpUtil.get("https://uonetplus-opiekun.vulcan.net.pl/" + clientId + "/" + userId + "/Start/Index/");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sends a GET request to Uonet Plus
     * and parses the result
     * @param action
     * @return parsed object
     */
    private Object apiGet(IAPIAction action) {
        String url = "https://uonetplus-opiekun.vulcan.net.pl/" + clientId + "/" + userId + action.getEndpoint();
        return action.parseResult(httpUtil.get(url));
    }

    public List<GradesTableRow> getGradesEntries() {
        return (List<GradesTableRow>)apiGet(new GetGrades());
    }
}
