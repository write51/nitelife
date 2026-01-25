package com.nitelife.demo;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class ExampleCode {
    public static void main(String[] args) throws ParseException {


        Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(format.format(calendar.getTime()));
    }
}
