package com.nitelife.demo.controller;

import com.nitelife.demo.service.EventService;
import com.nitelife.demo.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAll() {
        try {
            Pageable paging = PageRequest.of(0, 10, Sort.by("id"));
            Page<Event> pages;
            pages = eventService.findAll(paging);
            return pages.getContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public String get(@PathVariable Long id, Model model) {
        try {
            Event event = eventService.get(id);
            model.addAttribute("event", event);
            return "eventdetails";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("message", "HELLO THYMILIFI!");
        return "hello";
    }

    @PostMapping
    Event create(@RequestBody Event event) {
        return eventService.create(event);
    }

    @PutMapping("/{id}")
    Event update(@RequestBody Event event, @PathVariable Long id) {
        return eventService.update(event, id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        eventService.delete(id);
    }
}
