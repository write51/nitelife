package com.nitelife.demo.web.controller;

import com.nitelife.demo.business.service.EventService;
import com.nitelife.demo.business.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

    @GetMapping("/{year}/{month}/{day}")
    @ResponseBody
    public List<Event> getEventByDate(@PathVariable String year, @PathVariable String month, @PathVariable String day) {
        try {
            String date = year + "-" + month + "-" + day;
            List<Event> events = eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            return events;
        } catch (Exception e) {
            throw new RuntimeException(e);
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

}
