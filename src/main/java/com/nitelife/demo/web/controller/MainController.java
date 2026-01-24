package com.nitelife.demo.web.controller;

import com.nitelife.demo.business.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@Controller
public class MainController {

    private final EventService eventService;

    public MainController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/")
    public String redirectToIndex(final Model model) {
        try {
            model.addAttribute("events0", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01")));
            model.addAttribute("events1", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-02")));
            model.addAttribute("events2", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-03")));
            model.addAttribute("events3", this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-04")));
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
}