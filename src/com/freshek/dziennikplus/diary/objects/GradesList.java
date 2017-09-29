package com.freshek.dziennikplus.diary.objects;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Freshek on 29.09.17.
 */
public class GradesList extends ArrayList<Grade> {
    @Override
    public String toString() {
        return super.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}
