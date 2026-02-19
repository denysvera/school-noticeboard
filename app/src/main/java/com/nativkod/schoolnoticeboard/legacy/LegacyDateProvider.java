package com.nativkod.schoolnoticeboard.legacy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LegacyDateProvider {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static String getFriendlyDate(String rawDate) {
        try {
            Date date = formatter.parse(rawDate);
            // Returns a string like "Fri Jan 16 08:30:00 SAST 2026"
            return date.toString();
        } catch (Exception e) {
            return "Date Error";
        }
    }
}
