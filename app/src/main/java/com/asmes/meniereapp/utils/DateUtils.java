package com.asmes.meniereapp.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by stefy83 on 10/02/2018.
 */



public class DateUtils {

    public static Date addSubstractDays(Date date, int days) {

        //SimpleDateFormat dateParser = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = null;
        try {
            //Date myDate = dateParser.parse("01/01/2015");
            c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + days);

            //Date newDate = c.getTime();
            //String newFormattedDate = dateParser.format(newDate);//01/21/2015
        } catch (Exception e) {
            e.printStackTrace();
            //handle exception

        }
        return c.getTime();
    }
}
