package com.freshek.dziennikplus.diary.objects;

import com.freshek.dziennikplus.utils.Regex;
import org.jsoup.nodes.Element;

/**
 * Created by Freshek on 17.09.17.
 */
public class Grade {
    private int gradeValue;
    private GradeModifier gradeModifier;
    private int gradeWeight;
    private boolean isInvaild;

    private String textGrade;

    public Grade(int gradeValue, GradeModifier gradeModifier, int gradeWeight) {
        this.gradeValue = gradeValue;
        this.gradeModifier = gradeModifier;
        this.gradeWeight = gradeWeight;

        if (this.gradeValue == -1) {
            this.isInvaild = true;
        }
    }

    public Grade(String textGrade) {
        this.textGrade = textGrade;
        this.isInvaild = true;
    }

    public int getGradeValue() {
        return gradeValue;
    }

    public GradeModifier getGradeModifier() {
        return gradeModifier;
    }

    public int getGradeWeight() {
        return gradeWeight;
    }

    public boolean getIsInvaild() {
        return isInvaild;
    }

    public void setGradeValue(int value) {
        gradeValue = value;
    }

    public void setGradeModifier(GradeModifier value) {
        gradeModifier = value;
    }

    public void setGradeWeight(int value) {
        gradeWeight = value;
    }

    @Override
    public String toString() {
        // TODO: 18.09.17 Make the code more readable
        if (getIsInvaild())
            return textGrade;

        return getGradeValue() + (getGradeModifier() != GradeModifier.None ? (getGradeModifier() == GradeModifier.Plus ? "+" : "-") : "");
    }

    /**
     * Converts HTML element to a Grade object
     * @param gradeElement HTML grade element
     * @return Grade object
     */
    public static Grade gradeFromHtml(Element gradeElement) {
        if (!gradeElement.hasClass("ocenaCzastkowa") || !gradeElement.hasAttr("alt"))
            throw new IllegalArgumentException("The HTML element isn't a grade!");

        try {
            GradeModifier gradeModifier = GradeModifier.None;
            int gradeWeight;
            int gradeValue;

            String gradeText = gradeElement.text();

            if (gradeText.length() == 0)
                return new Grade(gradeText);

            gradeValue = Character.getNumericValue(gradeElement.text().charAt(0));

            if (gradeValue == -1)
                return new Grade(gradeText);

            if (gradeText.length() >= 2) {
                if (gradeText.charAt(1) == '+') {
                    gradeModifier = GradeModifier.Plus;
                } else if (gradeText.charAt(1) == '-') {
                    gradeModifier = GradeModifier.Minus;
                }
            }

            String alt = gradeElement.attr("alt");
            gradeWeight = Integer.parseInt(Regex.match("Waga: (\\d+)", alt).group(1));

            return new Grade(gradeValue, gradeModifier, gradeWeight);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

