package com.example.shybal.projectapp.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by shybal on 22/5/17.
 */

public class FourSquareApiAuth {
    private static final String authToken = "5R1QKOKO0YJHOPHL4SDA54AEWCQ5QJEA21ISQ5HUTEO0XGPY";
    private static String version;

    public static String getVersion() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyyMMdd");
        String strDate = currentDate.format(calendar.getTime());
        return strDate;
    }

    public static String getAuthToken() {
        return authToken;
    }
}
