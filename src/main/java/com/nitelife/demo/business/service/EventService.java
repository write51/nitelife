package com.nitelife.demo.business.service;

import com.nitelife.demo.business.Event;
import com.nitelife.demo.business.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository repository;

    @Autowired
    public EventService(EventRepository bookRepository) {
        this.repository = bookRepository;
    }

    public List<Event> all() {
        return repository.findAll();
    }

    public Page<Event> findAll(Pageable paging) {
        return repository.findAll(paging);
    }

    public List<Event> admineventsPagesGet(String page) {

//        DEFAULT PAGE: 0
//        DEFAULT SIZE: 32
//        DEFAULT SORT: id

        Pageable paging = PageRequest.of(Integer.valueOf(page), 32, Sort.by("id"));
        Page<Event> pages;

        try {

            pages = repository.findAll(paging);

            return pages.getContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Event get(Long id) {
        try {
            return repository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Event> getByDate(Date date) {
        try {
            return repository.findAllByDate(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Event create(Event event) {
        return repository.save(event);
    }

    public Event update(Event event, Long id) {

        Optional<Event> toUpdate = repository.findById(id);

        if (toUpdate.isEmpty()) {
            throw new RuntimeException();
        }

        Event b = toUpdate.get();


        repository.save(b);

        return b;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}