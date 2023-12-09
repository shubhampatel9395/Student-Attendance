package com.ams.studentattendance.database;

import android.os.Parcel;

import com.google.android.material.datepicker.CalendarConstraints;

import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

public class DateValidatorSunday implements CalendarConstraints.DateValidator {

    public DateValidatorSunday() {}

    private Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

    public static final Creator<DateValidatorSunday> CREATOR =
            new Creator<DateValidatorSunday>() {
                @Override
                public DateValidatorSunday createFromParcel(Parcel source) {
                    return new DateValidatorSunday();
                }

                @Override
                public DateValidatorSunday[] newArray(int size) {
                    return new DateValidatorSunday[size];
                }
            };

    @Override
    public boolean isValid(long date) {
        utc.setTimeInMillis(date);
        int dayOfWeek = utc.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SUNDAY;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DateValidatorSunday)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        Object[] hashedFields = {};
        return Arrays.hashCode(hashedFields);
    }
}