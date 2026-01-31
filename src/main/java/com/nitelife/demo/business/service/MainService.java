package com.nitelife.demo.business.service;

import com.nitelife.demo.business.Event;
import com.nitelife.demo.business.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MainService {

    @Autowired
    private EventRepository eventRepository;

    public MainService() {
        super();
    }

    public List<List<Event>> getMainList() throws ParseException {
        List<List<Event>> mainList = List.of(
            this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01")), /* Today */
            this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-02")), /* Today + 1 */
            this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-03")), /* Today + 2 */
            this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-04"))  /* Today + 3 */
        );
        return mainList;
    }

    public String getBackLink() throws ParseException {

        String currentDateString = "2025-01-01";

        Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDateString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String backLink = "http://localhost:8080/api/events/" + format.format(calendar.getTime()).replace("-", "/");

        return backLink;
    }

    public String getForwardLink() throws ParseException {

        String currentDateString = "2025-01-01";

        Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDateString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String forwardLink = "http://localhost:8080/api/events/" + format.format(calendar.getTime()).replace("-", "/");

        return forwardLink;
    }

    public boolean loginIsValid(String username, String password) {
        if (Objects.equals(username, "admin") && Objects.equals(password, "admin")) {
            return true;
        }
        return false;
    }

    class CalendarEvents {
        public List<List<Event>> week1;
        public List<List<Event>> week2;
        public List<List<Event>> week3;
        public List<List<Event>> week4;
        public List<List<Event>> week5;
    }

    public CalendarEvents getCalendar() throws ParseException {
        CalendarEvents calendarEvents = new CalendarEvents();
        calendarEvents.week1 = this.getCalendarWeek1();
        calendarEvents.week2 = this.getCalendarWeek2();
        calendarEvents.week3 = this.getCalendarWeek3();
        calendarEvents.week4 = this.getCalendarWeek4();
        calendarEvents.week5 = this.getCalendarWeek5();
        return calendarEvents;
    }

    public List<List<Event>> getCalendarWeek1() throws ParseException {
        List<List<Event>> week1 = new java.util.ArrayList<>(List.of());
        week1.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01")));
        week1.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-02")));
        week1.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-03")));
        week1.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-04")));
        week1.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-05")));
        return week1;
    }

    public List<List<Event>> getCalendarWeek2() throws ParseException {
        List<List<Event>> week2 = new java.util.ArrayList<>(List.of());
        for (int i = 6; i < 13; i++) {
            if (i < 10) {
                week2.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-0" + i)));
            } else {
                week2.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-" + i)));
            }
        }
        return week2;
    }

    public List<List<Event>> getCalendarWeek3() throws ParseException {
        List<List<Event>> week3 = new java.util.ArrayList<>(List.of());
        for (int i = 13; i < 20; i++) {
            week3.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-" + i)));
        }
        return week3;
    }

    public List<List<Event>> getCalendarWeek4() throws ParseException {
        List<List<Event>> week4 = new java.util.ArrayList<>(List.of());
        for (int i = 20; i < 27; i++) {
            week4.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-" + i)));
        }
        return week4;
    }

    public List<List<Event>> getCalendarWeek5() throws ParseException {
        List<List<Event>> week5 = new java.util.ArrayList<>(List.of());
        for (int i = 27; i < 32; i++) {
            week5.add(this.eventRepository.findAllByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-" + i)));
        }
        return week5;
    }

}
