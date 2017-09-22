package com.freshek.dziennikplus.diary.api.actions;

import com.freshek.dziennikplus.diary.objects.Grade;
import com.freshek.dziennikplus.diary.objects.GradesTableRow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Freshek on 18.09.17.
 */
public class GetGrades implements IAPIAction {
    private final String endpoint = "/Oceny.mvc/Wszystkie";

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public Object parseResult(String result) {
        Document document = Jsoup.parse(result);
        if (!document.title().contains("Oceny")) {
            throw new IllegalArgumentException("Unexpected document title");
        }

        //let's parse the table…
        List<Element> tables = document.getElementsByClass("ocenyZwykle-table");
        if (tables.size() == 0)
            throw new IllegalArgumentException("No table found in the HTML code");

        Element table = tables.get(0);

        List<Element> rows = table.getElementsByTag("tr");

        List<GradesTableRow> res = new ArrayList<>();
        for (int i = 1; i < rows.size(); i++) { //i = 1, because we skip first row
            List<Element> cells = rows.get(i).getElementsByTag("td");

            String subject = cells.get(0).text();

            List<Element> gradesHtml = cells.get(1).getElementsByTag("span");
            List<Grade> grades = new ArrayList<>();

            for (Element element : gradesHtml) {
                grades.add(Grade.gradeFromHtml(element));
            }

            String prAvg = cells.get(2).text();
            String cAvg = cells.get(3).text();

            res.add(new GradesTableRow(subject, grades, prAvg, cAvg));
        }

        return res;
    }
}
