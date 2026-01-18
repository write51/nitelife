package com.nitelife.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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



    public Event get(Long id) {
        try {
            return repository.findById(id).get();
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