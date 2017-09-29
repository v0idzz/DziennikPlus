package com.freshek.dziennikplus.utils.http;

import com.freshek.dziennikplus.diary.objects.Grade;
import com.freshek.dziennikplus.diary.objects.GradeModifier;

import java.util.List;

/**
 * Created by Freshek on 21.09.17.
 *
 * A simple math util class
 */
public class MathUtil {

    /**
     * Calculates an avg of the grades
     * @param grades grades to calc the avg
     *               from
     * @return the avg of grades
     */
    public static float calcAvgOfGrades(List<Grade> grades) {
        float sum = 0f;
        float count = 0f;
        for (Grade g : grades) {
            if (g.getIsInvaild()) continue;

            count += g.getGradeWeight();
            sum += g.getGradeValue() * g.getGradeWeight();
            if (g.getGradeModifier() == GradeModifier.Plus)
                sum += 0.50f;
            else if (g.getGradeModifier() == GradeModifier.Minus)
                sum -= 0.25f;
        }
        return sum / count;
    }
}
