package com.nitelife.demo.web.controller;

import com.nitelife.demo.business.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Controller
public class MainController {

    private final EventService eventService;

    private final String CURRENT_DATE = "2025-02-10";

    public MainController(EventService eventService) {
        this.eventService = eventService;
    }

    private String getBackLink(String currentDateString) throws ParseException {

        Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDateString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(calendar.getTime()).replace("-", "/");
    }

    private String getForwardLink(String currentDateString) throws ParseException {

            Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            return format.format(calendar.getTime()).replace("-", "/");
    }


    @GetMapping("/")
    public String redirectToIndex(final Model model) {
        try {
            model.addAttribute("events0", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01")));
            model.addAttribute("events1", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-02")));
            model.addAttribute("events2", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-03")));
            model.addAttribute("events3", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-04")));

            model.addAttribute("backLink", "http://localhost:8080/api/events/" + getBackLink("2025-01-01"));
            model.addAttribute("forwardLink", "http://localhost:8080/api/events/" + getForwardLink("2025-01-01"));



            return "index";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/login")
    public String redirectToLogin() {
        return "login";
    }

    @GetMapping("/suggest")
    public String redirectToSuggest() {
        return "suggest";
    }

    @PostMapping("/submitlogin")
    public String submitLogin(@RequestBody String loginCredentials) {
        System.out.println("loginCredentials -> " + "'" + loginCredentials + "'");
        if (Objects.equals(loginCredentials, "uname=admin&pword=admin")) {
            return "adminpanel";
        }
        return "index";
    }
}