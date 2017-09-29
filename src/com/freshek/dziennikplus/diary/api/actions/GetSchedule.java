package com.freshek.dziennikplus.diary.api.actions;

import com.freshek.dziennikplus.diary.objects.tables.ScheduleTableRow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Freshek on 28.09.17.
 */
public class GetSchedule implements IAPIAction {

    @Override
    public String getEndpoint() {
        String endpoint = "/Lekcja.mvc/PlanLekcji";
        return endpoint;
    }

    @Override
    public Object parseResult(String result) {
        Document document = Jsoup.parse(result);

        List<Element> tables = document.getElementsByClass("presentData");

        if (tables.size() == 0)
            throw new InvalidParameterException("The parameter doesn't contain a schedule table");

        Element table = tables.get(0);

        /*
                     table to parse (a fragment):
        +------+---------------+--------------+--------------+
        | l_id |    l_time     |    Monday    |    Tuesday   |
        +------+---------------+--------------+--------------+
        |  1   |  08:00 08:45  |   Lesson 1   |   Lesson 1   |
        +------+---------------+--------------+--------------+
        |  2   |  08:55 09:40  |   Lesson 2   |   Lesson 2   |
        +------+---------------+--------------+--------------+
         */

        List<Element> rows = table.getElementsByTag("tr");

        List<ScheduleTableRow> scheduleTableRows = new ArrayList<>();

        for (int i = 1; i < rows.size(); i++) {
            List<Element> cells = rows.get(i).getElementsByTag("td");

            int lessonId;
            LocalTime lessonBeginTime, lessonEndTime;
            String mondayLesson;
            String tuesdayLesson;
            String wednesdayLesson;
            String thursdayLesson;
            String fridayLesson;

            try {
                lessonId = Integer.parseInt(cells.get(0).text());

                String[] lessonsTime = cells.get(1).text().split(" ");

                lessonBeginTime = LocalTime.parse(lessonsTime[0]);
                lessonEndTime = LocalTime.parse(lessonsTime[1]);

                mondayLesson = cells.get(2).text();
                tuesdayLesson = cells.get(3).text();
                wednesdayLesson = cells.get(4).text();
                thursdayLesson = cells.get(5).text();
                fridayLesson = cells.get(6).text();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            scheduleTableRows.add(new ScheduleTableRow(lessonId, lessonBeginTime, lessonEndTime, mondayLesson, tuesdayLesson, wednesdayLesson, thursdayLesson, fridayLesson));
        }

        return scheduleTableRows;
    }
}
