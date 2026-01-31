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
}
