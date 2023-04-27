package me.eriknikli.neptuncopy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// https://stackoverflow.com/questions/47252233/how-to-store-timestamp-on-firestore-using-android-edittext-and-datetimepicker-di
public class DateHandler {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getDateFromString(String datetoSaved) {

        try {
            return format.parse(datetoSaved);
        } catch (ParseException e) {
            return null;
        }

    }
}
