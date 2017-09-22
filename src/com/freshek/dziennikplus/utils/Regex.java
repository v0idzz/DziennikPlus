package com.freshek.dziennikplus.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Freshek on 20.09.17.
 *
 * A simple regex util
 */
public class Regex {

    /**
     * Matches the String using
     * the Pattern and returns
     * the result
     * @param pattern pattern to match
     * @param s string to match
     * @return match
     */
    public static Matcher match(String pattern, String s) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(s);
        if (!m.find())
            return null;
        return m;
    }
}
