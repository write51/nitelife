package com.nitelife.demo.repository;

import com.nitelife.demo.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAll(Pageable pageable);
    //Page<Event> findAllByTitle(String title, Pageable pageable);
    //Page<Book> findAllByAuthor(String author, Pageable pageable);
}
