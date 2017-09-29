package com.freshek.dziennikplus.diary.objects.tables;

import java.time.LocalTime;

/**
 * Created by Freshek on 28.09.17.
 */
public class ScheduleTableRow {
    private int lessonId;
    private LocalTime lessonStart;
    private LocalTime lessonEnd;
    private String mondayLesson;
    private String tuesdayLesson;
    private String wednesdayLesson;
    private String thursdayLesson;
    private String fridayLesson;

    public ScheduleTableRow(int lessonId, LocalTime lessonStart, LocalTime lessonEnd, String mondayLesson, String tuesdayLesson, String wednesdayLesson, String thursdayLesson, String fridayLesson) {
        this.lessonId = lessonId;
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
        this.mondayLesson = mondayLesson;
        this.tuesdayLesson = tuesdayLesson;
        this.wednesdayLesson = wednesdayLesson;
        this.thursdayLesson = thursdayLesson;
        this.fridayLesson = fridayLesson;
    }

    public int getLessonId() {
        return lessonId;
    }

    public LocalTime getLessonStart() {
        return lessonStart;
    }

    public LocalTime getLessonEnd() {
        return lessonEnd;
    }

    public String getMondayLesson() {
        return mondayLesson;
    }

    public String getTuesdayLesson() {
        return tuesdayLesson;
    }

    public String getWednesdayLesson() {
        return wednesdayLesson;
    }

    public String getThursdayLesson() {
        return thursdayLesson;
    }

    public String getFridayLesson() {
        return fridayLesson;
    }
}
