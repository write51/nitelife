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

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public String getEvent(@PathVariable Long id, final Model model) {
        model.addAttribute("event", this.eventService.get(id));
        return "event";
    }

    @GetMapping("/{year}/{month}/{day}")
    public String getEventByDate(@PathVariable String year, @PathVariable String month, @PathVariable String day, @RequestParam(required = false) String filterCategory, final Model model) throws ParseException {
        String date = year + "-" + month + "-" + day;
        model.addAttribute("mainList", this.eventService.getMainList(date, filterCategory));
        model.addAttribute("backLink", this.eventService.getBackLink(date, filterCategory));
        model.addAttribute("forwardLink", this.eventService.getForwardLink(date, filterCategory));
        model.addAttribute("filterUrl", this.eventService.getFilterUrl(year, month, day));
        model.addAttribute("filterCategoryActive", this.eventService.getFilterCategoryActive(filterCategory));
        return "date";
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.create(event);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@RequestBody Event event, @PathVariable Long id) {
        return eventService.update(event, id);
    }

    @DeleteMapping("/{id}")
    void deleteEvent(@PathVariable Long id) {
        eventService.delete(id);
    }

}
