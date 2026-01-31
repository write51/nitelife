package com.nitelife.demo.business.service;

import com.nitelife.demo.business.Event;
import com.nitelife.demo.business.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventService() {
        super();
    }

    public List<Event> all() {
        return eventRepository.findAll();
    }

    public Page<Event> findAll(Pageable paging) {
        return eventRepository.findAll(paging);
    }

    public List<Event> admineventsPagesGet(String page) {
        /*
        DEFAULT PAGE: 0
        DEFAULT SIZE: 32
        DEFAULT SORT: id
        */
        if (page == null) {
            page = "0";
        }
        Pageable paging = PageRequest.of(Integer.parseInt(page), 32, Sort.by("id"));
        Page<Event> pages;
        pages = eventRepository.findAll(paging);
        return pages.getContent();
    }

    public String admineventsPagesGetPreviousPage(String page) {
        if (page == null || (Integer.parseInt(page) == 0)) {
            return "0";
        } else if (Integer.parseInt(page) >= 1) {
            return String.valueOf(Integer.parseInt(page) - 1);
        }
        return "0";
    }
    public String admineventsPagesGetNextPage(String page) {
        if (page == null) {
            return "1";
        }
        return String.valueOf(Integer.parseInt(page) + 1);
    }

    public Event get(Long id) {
        return eventRepository.findById(id).get();
    }

    public List<Event> getByDate(Date date) {
        return eventRepository.findAllByDate(date);
    }

    public List<Event> getByDateAndFilter(Date date, String filterCategory) {
        return eventRepository.findAllByDateAndCategory(date, filterCategory);
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public Event update(Event event, Long id) {
        Optional<Event> toUpdate = eventRepository.findById(id);
        if (toUpdate.isEmpty()) {
            throw new RuntimeException();
        }
        Event b = toUpdate.get();
        eventRepository.save(b);
        return b;
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

}
