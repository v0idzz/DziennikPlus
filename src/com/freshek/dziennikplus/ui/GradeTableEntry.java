package com.freshek.dziennikplus.ui;

import com.freshek.dziennikplus.diary.objects.Grade;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Freshek on 17.09.17.
 */
public class GradeTableEntry {
    private final SimpleStringProperty subject;
    private final SimpleStringProperty grades;
    private final SimpleStringProperty predictedGradesAvg;
    private final SimpleStringProperty gradesAvg;
    private final SimpleStringProperty calculatedGradesAvg;

    private final List<Grade> gradesList;

    public GradeTableEntry(String subject, List<Grade> gradesList, String predictedGradesAvg, String gradesAvg, String calculatedGradesAvg) {
        this.subject = new SimpleStringProperty(subject);
        this.grades = new SimpleStringProperty(gradesToString(gradesList));
        this.predictedGradesAvg = new SimpleStringProperty(predictedGradesAvg);
        this.gradesAvg = new SimpleStringProperty(gradesAvg);
        this.calculatedGradesAvg = new SimpleStringProperty(calculatedGradesAvg);
        this.gradesList = gradesList;
    }

    private String gradesToString(List<Grade> gradesList) {
        return gradesList.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    public String getSubject() {
        return subject.get();
    }

    public String getGrades() {
        return grades.get();
    }

    public String getPredictedGradesAvg() {
        return predictedGradesAvg.get();
    }

    public String getGradesAvg() {
        return gradesAvg.get();
    }

    public String getCalculatedGradesAvg() {
        return calculatedGradesAvg.get();
    }

    public List<Grade> getGradesList() {
        return gradesList;
    }

    public void setSubject(String value) {
        subject.set(value);
    }

    public void setGrades(String value) {
        grades.set(value);
    }

    public void setPredictedGradesAvg(String value) {
        predictedGradesAvg.set(value);
    }

    public void setGradesAvg(String value) {
        gradesAvg.set(value);
    }

    public void setCalculatedGradesAvg(String value) {
        calculatedGradesAvg.set(value);
    }

    public void setGradesList(List<Grade> value) {
        grades.set(gradesToString(value));
    }
}
