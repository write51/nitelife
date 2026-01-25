package com.nitelife.demo.business.repository;

import com.nitelife.demo.business.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAll(Pageable pageable);
    List<Event> findAllByDate(Date date);
    List<Event> findAllByDateAndCategory(Date date, String category);
}
