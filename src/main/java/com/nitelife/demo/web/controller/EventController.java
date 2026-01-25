package com.nitelife.demo.web.controller;

import com.nitelife.demo.business.service.EventService;
import com.nitelife.demo.business.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //    @GetMapping("/hello")
    //    public String hello(Model model) {
    //        model.addAttribute("message", "HELLO THYMILIFI!");
    //        return "hello";
    //    }

    @GetMapping("/{id}")
    public String getEvent(@PathVariable Long id, final Model model) {
        try {
            model.addAttribute("event", this.eventService.get(id));
            return "event";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

/*    @GetMapping("/{year}/{month}/{day}")
    @ResponseBody
    public String getEventByDate(@PathVariable String year, @PathVariable String month, @PathVariable String day, final Model model) {
        try {
            String date = year + "-" + month + "-" + day;
            List<Event> events = eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            return "date";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    @GetMapping("/{year}/{month}/{day}")
    public String getEventByDate(@PathVariable String year, @PathVariable String month, @PathVariable String day, @RequestParam(required = false) String filterCategory, final Model model) {

        System.out.println(filterCategory);

        if (filterCategory != null) {
            try {
                String date = year + "-" + month + "-" + day;

                model.addAttribute("events0", this.eventService.getByDateAndFilter(new SimpleDateFormat("yyyy-MM-dd").parse(date), filterCategory));
                model.addAttribute("events1", this.eventService.getByDateAndFilter(new SimpleDateFormat("yyyy-MM-dd").parse(getForwardLinkNoReplace(date)), filterCategory));
                model.addAttribute("events2", this.eventService.getByDateAndFilter(new SimpleDateFormat("yyyy-MM-dd").parse(getForwardLinkNoReplace(getForwardLinkNoReplace(date))), filterCategory));
                model.addAttribute("events3", this.eventService.getByDateAndFilter(new SimpleDateFormat("yyyy-MM-dd").parse(getForwardLinkNoReplace(getForwardLinkNoReplace(getForwardLinkNoReplace(date)))), filterCategory));

                model.addAttribute("backLink", "http://localhost:8080/api/events/" + getBackLink(date) + "?filterCategory=" + filterCategory);
                model.addAttribute("forwardLink", "http://localhost:8080/api/events/" + getForwardLink(date) + "?filterCategory=" + filterCategory);

                model.addAttribute("filterUrl", "http://localhost:8080/api/events/" + year + '/' + month + '/' + day + "?filterCategory=");

                return "date";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {

        try {
            String date = year + "-" + month + "-" + day;

            model.addAttribute("events0", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date)));
            model.addAttribute("events1", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse(getForwardLinkNoReplace(date))));
            model.addAttribute("events2", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse(getForwardLinkNoReplace(getForwardLinkNoReplace(date)))));
            model.addAttribute("events3", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse(getForwardLinkNoReplace(getForwardLinkNoReplace(getForwardLinkNoReplace(date))))));

            model.addAttribute("backLink", "http://localhost:8080/api/events/" + getBackLink(date));
            model.addAttribute("forwardLink", "http://localhost:8080/api/events/" + getForwardLink(date));

            model.addAttribute("filterUrl", "http://localhost:8080/api/events/" + year + '/' + month + '/' + day + "?filterCategory=");

            return "date";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        }
    }




    @PostMapping
    Event createEvent(@RequestBody Event event) {
        return eventService.create(event);
    }

    @PutMapping("/{id}")
    Event updateEvent(@RequestBody Event event, @PathVariable Long id) {
        return eventService.update(event, id);
    }

    @DeleteMapping("/{id}")
    void deleteEvent(@PathVariable Long id) {
        eventService.delete(id);
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

    private String getForwardLinkNoReplace(String currentDateString) throws ParseException {

        Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentDateString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(calendar.getTime());
    }

}
