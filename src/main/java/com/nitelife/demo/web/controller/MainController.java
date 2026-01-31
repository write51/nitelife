package com.nitelife.demo.web.controller;

import com.nitelife.demo.business.Event;
import com.nitelife.demo.business.service.EventService;
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
            List<List<Event>> mainList = List.of(
                this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01")), /* Today */
                this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-02")), /* Today + 1 */
                this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-03")), /* Today + 2 */
                this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-04"))  /* Today + 3 */
            );
            model.addAttribute("mainList", mainList);
            model.addAttribute("backLink", "http://localhost:8080/api/events/" + getBackLink("2025-01-01"));
            model.addAttribute("forwardLink", "http://localhost:8080/api/events/" + getForwardLink("2025-01-01"));
            return "index";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private class LoginForm {
        String username;
        String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @GetMapping("/login")
    public String redirectToLogin(Model model) {
        model.addAttribute("loginDetails", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String submitLogin(@ModelAttribute LoginForm loginDetails, Model model) {
        if (Objects.equals(loginDetails.username, "admin") && Objects.equals(loginDetails.password, "admin")) {
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
        //this.eventService.delete(id);
        model.addAttribute("event", this.eventService.get(id));
        return "admin/delete_confirmation";
    }

    private class EditEventAdminPanelForm {
        String name;
        String date;
        String time;
        String description;
        String category;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
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

        event.setName(eventDetails.name);
        event.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-10"));
        event.setTime(eventDetails.time);
        event.setDescription(eventDetails.description);
        event.setCategory(eventDetails.category);

        Event updated = this.eventService.update(event, id);

        return redirectToAdminPanelEvents(null, model);
    }

    @GetMapping("/adminpanel/events/delete/{id}/confirm")
    public String deleteAreYouSureConfirm(@PathVariable Long id, final Model model) {
        this.eventService.delete(id);
        return redirectToAdminPanelEvents(null, model);
        //return "admin/adminpanelevents";
    }

    private class SuggestEventForm {
        String name;
        String date;
        String time;
        String description;
        String category;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    @GetMapping("/suggestevent")
    public String redirectToSuggest(Model model) {
        model.addAttribute("eventDetails", new SuggestEventForm());
        return "suggest";
    }

    @PostMapping("/suggestevent")
    public String suggestAnEvent(@ModelAttribute SuggestEventForm eventDetails, Model model) throws ParseException {

        Event event = new Event();
        event.setName(eventDetails.name);
        // FIXME:
        // Should parse date provided by form. First mangle it to correct format, then parse.
        // event.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-10"));
        event.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-10-10"));
        event.setTime(eventDetails.time);
        event.setDescription(eventDetails.description);
        event.setCategory(eventDetails.category);

        eventService.create(event);

        return "index";
    }

    @GetMapping("/calendar")
    public String adminEdit(final Model model) throws ParseException {

        // TODO: Algorithm to calculate monthly events.

        List<List<Event>> week1 = new java.util.ArrayList<>(List.of());
        List<List<Event>> week2 = new java.util.ArrayList<>(List.of());
        List<List<Event>> week3 = new java.util.ArrayList<>(List.of());
        List<List<Event>> week4 = new java.util.ArrayList<>(List.of());
        List<List<Event>> week5 = new java.util.ArrayList<>(List.of());

        List<Event> eventList;
        week1.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-01")));
        week1.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-02")));
        week1.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-03")));
        week1.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-04")));
        week1.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-05")));
        for (int i = 6; i < 13; i++) {
            if (i < 10) {
                week2.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-0" + i)));
            } else {
                week2.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-" + i)));
            }
        }
        for (int i = 13; i < 20; i++) {
            week3.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-" + i)));
        }
        for (int i = 20; i < 27; i++) {
            week4.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-" + i)));
        }
        for (int i = 27; i < 32; i++) {
            week5.add(this.eventService.getByDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-01-" + i)));
        }

        model.addAttribute("week1", week1);
        model.addAttribute("week2", week2);
        model.addAttribute("week3", week3);
        model.addAttribute("week4", week4);
        model.addAttribute("week5", week5);

        return "calendar";
    }

}
