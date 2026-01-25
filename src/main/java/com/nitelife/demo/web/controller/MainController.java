package com.nitelife.demo.web.controller;

import com.nitelife.demo.business.Event;
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

    @GetMapping("/adminpanel")
    public String redirectToAdminPanel(final Model model) {
        model.addAttribute("eventNumber", this.eventService.all().size());
        return "adminpanel";
    }

    @GetMapping("/adminpanel/events")
    public String redirectToAdminPanelEvents(@RequestParam(required = false) String page, final Model model) {
        if (page==null) {
            model.addAttribute("events", this.eventService.admineventsPagesGet("0"));
            return "adminpanelevents";
        }
        model.addAttribute("events", this.eventService.admineventsPagesGet(page));
        return "adminpanelevents";
    }

    @GetMapping("/adminpanel/events/delete/{id}")
    public String deleteAreYouSure(@PathVariable Long id, final Model model) {
        //this.eventService.delete(id);
        model.addAttribute("event", this.eventService.get(id));
        return "admin/delete_confirmation";
    }

    @GetMapping("/adminpanel/events/edit/{id}")
    public String adminEdit(@PathVariable Long id, final Model model) {
        model.addAttribute("event", this.eventService.get(id));
        return "admin/edit";
    }

    @PostMapping("/adminpanel/events/edit/{id}/update")
    public String adminEditUpdateEvent(@PathVariable Long id, @RequestBody String eventDetails) throws ParseException {
        // http://localhost:8080/adminpanel/events/edit/12/update

        //Event event = model.addAttribute("event", this.eventService.get(id));

        // FIXME: Hehe...
        String name = "";
        String date = "";
        String time = "";
        String description = "";
        String category = "";

        try {
            name = eventDetails.split("&")[0].split("=")[1];
        } catch (Exception e) {
            name="";
        }
        try {
            date = eventDetails.split("&")[1].split("=")[1];
        } catch (Exception e) {
            date = "";
        }
        try {
            time = eventDetails.split("&")[2].split("=")[1];
        } catch (Exception e) {
            time = "";
        }
        try {
            description = eventDetails.split("&")[3].split("=")[1];
        } catch (Exception e) {
            description = "";
        }
        try {
            category = eventDetails.split("&")[4].split("=")[1];
        } catch (Exception e) {
            category = "";
        }


        Event event = this.eventService.get(id);

        event.setName(name);
        event.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-10"));
        event.setTime(time);
        event.setDescription(description);
        event.setCategory(category);

        Event updated = this.eventService.update(event, id);

        return "adminpanelevents";
    }



    @GetMapping("/adminpanel/events/delete/{id}/confirm")
    public String deleteAreYouSureConfirm(@PathVariable Long id, final Model model) {
        this.eventService.delete(id);
        return "adminpanelevents";
    }

    @PostMapping("/submitlogin")
    public String submitLogin(@RequestBody String loginCredentials) {
        System.out.println("loginCredentials -> " + "'" + loginCredentials + "'");
        if (Objects.equals(loginCredentials, "uname=admin&pword=admin")) {
            return "adminpanel";
        }
        return "index";
    }

//    private Event eventStringToObject(String eventDetails) {
//
//    }

    @PostMapping("/suggestevent")
    public String suggestAnEvent(@RequestBody String eventDetails) throws ParseException {

        // FIXME: Incoming strings should be converted from URL format to normal string.
        String name = eventDetails.split("&")[0].split("=")[1];
        String date = eventDetails.split("&")[1].split("=")[1];
        String time = eventDetails.split("&")[2].split("=")[1];
        String description = eventDetails.split("&")[3].split("=")[1];
        String category = eventDetails.split("&")[4].split("=")[1];

        /*
        NAME: This+is+an+Fun+Event+name+and+is+really+fun
        DATE: 19.01.2024.
        TIME: 18%3A30
        DESCRIPTION: Submitting+an+event+Starcarft+is+awesome+and+so+is+Warcraft+and+stuff.+Also+Tiny+Tycoon+Universe.
        CATEGORY: Mjuzza
        */

        Event event = new Event();
        event.setName(name);
        event.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-10"));
        event.setTime(time);
        event.setDescription(description);
        event.setCategory(category);

        eventService.create(event);

        return "index";
    }
}