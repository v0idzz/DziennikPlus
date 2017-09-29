package com.freshek.dziennikplus.ui;

import com.freshek.dziennikplus.diary.objects.Grade;
import com.freshek.dziennikplus.diary.objects.GradesList;
import com.freshek.dziennikplus.utils.http.MathUtil;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Freshek on 17.09.17.
 */
public class GradeTableEntry {
    private final SimpleStringProperty subject;
    private final SimpleObjectProperty<GradesList> grades;
    private final SimpleStringProperty predictedGradesAvg;
    private final SimpleStringProperty gradesAvg;
    private final SimpleFloatProperty calculatedGradesAvg;
    private final SimpleStringProperty calculatedGradesAvgStr;

    public GradeTableEntry(String subject, GradesList gradesList, String predictedGradesAvg, String gradesAvg) {
        this.subject = new SimpleStringProperty(subject);
        this.grades = new SimpleObjectProperty<>(gradesList);
        this.predictedGradesAvg = new SimpleStringProperty(predictedGradesAvg);
        this.gradesAvg = new SimpleStringProperty(gradesAvg);
        this.calculatedGradesAvg = new SimpleFloatProperty(MathUtil.calcAvgOfGrades(gradesList));
        this.calculatedGradesAvgStr = new SimpleStringProperty(Float.isNaN(this.calculatedGradesAvg.get()) ? "-" : String.valueOf(this.calculatedGradesAvg.get()));
    }

    private String gradesToString(List<Grade> gradesList) {
        return gradesList.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    public String getSubject() {
        return subject.get();
    }

    public String getGrades() {
        return grades.get().size() != 0 ? grades.get().toString() : "brak ocen";
    }

    public String getPredictedGradesAvg() {
        return predictedGradesAvg.get();
    }

    public String getGradesAvg() {
        return gradesAvg.get();
    }

    public float getCalculatedGradesAvg() {
        return calculatedGradesAvg.get();
    }

    public GradesList getGradesList() {
        return grades.get();
    }

    public String getCalculatedGradesAvgStr() {
        return calculatedGradesAvgStr.get();
    }

    public void setGradesList(GradesList value) {
        grades.set(value);
    }
}
