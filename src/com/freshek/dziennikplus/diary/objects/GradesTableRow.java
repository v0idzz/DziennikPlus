package com.freshek.dziennikplus.diary.objects;

import java.util.List;

/**
 * Created by Freshek on 20.09.17.
 */
public class GradesTableRow {
    private String subject;
    private List<Grade> grades;
    private String predictedGradesAvg;
    private String gradesAvg;

    public GradesTableRow(String subject, List<Grade> grades, String predictedGradesAvg, String gradesAvg) {
        this.subject = subject;
        this.grades = grades;
        this.predictedGradesAvg = predictedGradesAvg;
        this.gradesAvg = gradesAvg;
    }

    public String getSubject() {
        return subject;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public String getPredictedGradesAvg() {
        return predictedGradesAvg;
    }

    public String getGradesAvg() {
        return gradesAvg;
    }
}
