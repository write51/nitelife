package com.nitelife.demo.web.controller;

import com.nitelife.demo.business.Event;
import com.nitelife.demo.business.service.EventService;
import com.nitelife.demo.business.service.MainService;
import com.nitelife.demo.web.forms.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {

    private EventService eventService;
    private MainService mainService;

    private final String CURRENT_DATE = "2025-02-10";

    public MainController(EventService eventService, MainService mainService) {
        this.eventService = eventService;
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String redirectToIndex(final Model model) throws ParseException {
        model.addAttribute("mainList", this.mainService.getMainList());
        model.addAttribute("backLink", this.mainService.getBackLink());
        model.addAttribute("forwardLink", this.mainService.getForwardLink());
        return "index";
    }



    @GetMapping("/login")
    public String redirectToLogin(Model model) {
        model.addAttribute("loginDetails", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String submitLogin(@ModelAttribute LoginForm loginDetails, Model model) {
        if (this.mainService.loginIsValid(loginDetails.getUsername(), loginDetails.getPassword())) {
            return redirectToAdminPanel(model);
        }
        return "index";
    }

    @GetMapping("/adminpanel")
    public String redirectToAdminPanel(final Model model) {
        model.addAttribute("eventNumber", this.eventService.all().size());
        return "admin/adminpanel";
    }

    @GetMapping("/adminpanel/events")
    public String redirectToAdminPanelEvents(@RequestParam(required = false) String page, final Model model) {
        if (page==null) {
            model.addAttribute("events", this.eventService.admineventsPagesGet("0"));
            model.addAttribute("previousPage", "0");
            model.addAttribute("nextPage", "1");
            return "admin/adminpanelevents";
        }
        model.addAttribute("events", this.eventService.admineventsPagesGet(page));
        if (Integer.valueOf(page) >= 1) {
            model.addAttribute("previousPage", String.valueOf(Integer.valueOf(page) - 1));
        } else if (Integer.valueOf(page) == 0) {
            model.addAttribute("previousPage", "0");
        }
        model.addAttribute("nextPage", String.valueOf(Integer.valueOf(page) + 1));
        return "admin/adminpanelevents";
    }

    @GetMapping("/adminpanel/events/delete/{id}")
    public String deleteAreYouSure(@PathVariable Long id, final Model model) {
        model.addAttribute("event", this.eventService.get(id));
        return "admin/delete_confirmation";
    }

    @GetMapping("/adminpanel/events/edit/{id}")
    public String adminEdit(@PathVariable Long id, final Model model) {
        EditEventAdminPanelForm eventDetails = new EditEventAdminPanelForm();
        Event event = this.eventService.get(id);
        eventDetails.setName(event.getName());
        eventDetails.setDate("2025-01-01"); // FIXME: Date should be Date, not String.
        eventDetails.setTime(event.getTime());
        eventDetails.setDescription(event.getDescription());
        eventDetails.setCategory(event.getCategory());
        model.addAttribute("eventId", id);
        model.addAttribute("eventDetails", eventDetails);
        return "admin/edit";
    }

    @PostMapping("/adminpanel/events/edit/{id}")
    public String adminEditUpdateEvent(@PathVariable Long id, @ModelAttribute EditEventAdminPanelForm eventDetails, Model model) throws ParseException {

        Event event = this.eventService.get(id);

        event.setName(eventDetails.getName());
        event.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-10"));
        event.setTime(eventDetails.getTime());
        event.setDescription(eventDetails.getDescription());
        event.setCategory(eventDetails.getCategory());

        Event updated = this.eventService.update(event, id);

        return redirectToAdminPanelEvents(null, model);
    }

    @GetMapping("/adminpanel/events/delete/{id}/confirm")
    public String deleteAreYouSureConfirm(@PathVariable Long id, final Model model) {
        this.eventService.delete(id);
        return redirectToAdminPanelEvents(null, model);
        //return "admin/adminpanelevents";
    }



    @GetMapping("/suggestevent")
    public String redirectToSuggest(Model model) {
        model.addAttribute("eventDetails", new SuggestEventForm());
        return "suggest";
    }

    @PostMapping("/suggestevent")
    public String suggestAnEvent(@ModelAttribute SuggestEventForm eventDetails, Model model) throws ParseException {

        Event event = new Event();
        event.setName(eventDetails.getName());
        // FIXME:
        // Should parse date provided by form. First mangle it to correct format, then parse.
        // event.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-10"));
        event.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-10"));
        event.setTime(eventDetails.getTime());
        event.setDescription(eventDetails.getDescription());
        event.setCategory(eventDetails.getCategory());

        eventService.create(event);

        return "index";
    }

    @GetMapping("/calendar")
    public String adminEdit(final Model model) throws ParseException {
        model.addAttribute("week1", this.mainService.getCalendarWeek1());
        model.addAttribute("week2", this.mainService.getCalendarWeek2());
        model.addAttribute("week3", this.mainService.getCalendarWeek3());
        model.addAttribute("week4", this.mainService.getCalendarWeek4());
        model.addAttribute("week5", this.mainService.getCalendarWeek5());
        return "calendar";
    }

}
