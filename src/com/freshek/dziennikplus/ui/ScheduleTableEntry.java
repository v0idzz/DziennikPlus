package com.freshek.dziennikplus.ui;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.time.LocalTime;

/**
 * Created by Freshek on 28.09.17.
 */
public class ScheduleTableEntry {
    private final SimpleStringProperty lessonId;
    private final SimpleStringProperty lessonTime;
    private final SimpleStringProperty mondayLesson;
    private final SimpleStringProperty tuesdayLesson;
    private final SimpleStringProperty wednesdayLesson;
    private final SimpleStringProperty thursdayLesson;
    private final SimpleStringProperty fridayLesson;

    public ScheduleTableEntry(int lessonId, LocalTime lessonStartTime, LocalTime lessonEndTime, String mondayLesson, String tuesdayLesson, String wednesdayLesson, String thursdayLesson, String fridayLesson) {
        this.lessonId = new SimpleStringProperty(String.valueOf(lessonId));

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        this.lessonTime = new SimpleStringProperty(lessonStartTime + " → " + lessonEndTime);
        this.mondayLesson = new SimpleStringProperty(mondayLesson);
        this.tuesdayLesson = new SimpleStringProperty(tuesdayLesson);
        this.wednesdayLesson = new SimpleStringProperty(wednesdayLesson);
        this.thursdayLesson = new SimpleStringProperty(thursdayLesson);
        this.fridayLesson = new SimpleStringProperty(fridayLesson);
    }

    public String getLessonId() {
        return lessonId.get();
    }

    public String getLessonTime() {
        return lessonTime.get();
    }

    public String getMondayLesson() {
        return mondayLesson.get();
    }

    public String getTuesdayLesson() {
        return tuesdayLesson.get();
    }

    public String getWednesdayLesson() {
        return wednesdayLesson.get();
    }

    public String getThursdayLesson() {
        return thursdayLesson.get();
    }

    public String getFridayLesson() {
        return fridayLesson.get();
    }
}
