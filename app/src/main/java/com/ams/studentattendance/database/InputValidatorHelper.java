package com.ams.studentattendance.database;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidatorHelper {
    public boolean isValidEmail(String in) {
        return !TextUtils.isEmpty(in) && Patterns.EMAIL_ADDRESS.matcher(in).matches();
    }

    public boolean isValidPassword(String in) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(in);

        return matcher.matches();
    }

    public boolean isNullOrEmpty(String in) {
        return TextUtils.isEmpty(in);
    }

    public boolean isNumeric(String in) {
        return TextUtils.isDigitsOnly(in);
    }

    public boolean isValidPhoneNumber(String in) {
        Pattern pattern;
        Matcher matcher;

        final String PHONE_NUMBER_PATTERN = "((\\+*)((0[ -]*)*|((91 )*))((\\d{12})+|(\\d{10})+))|\\d{5}([- ]*)\\d{6}";

        pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        matcher = pattern.matcher(in);

        return matcher.matches();
    }

    public String parseDate(String time) {
        String inputPattern = "dd MMM yyyy"; // Material Date picker default date format
        //String inputPattern = "EEE MMM dd HH:mm:ss zzzz yyyy"; // Android default date format
        String outputPattern = "dd-MM-yyyy"; // What we want date format
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date;
        String str = null;

        try {
            date = inputFormat.parse(time);
            if (date != null) {
                str = outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
