package com.example.demo.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event>{
    public Page<Event> findByNameContaining(String name, Pageable pageable);
    public Page<Event> findByfree(boolean free, Pageable pageable);
    public Page<Event> findByBasePriceGreaterThanEqual(int basePrice, Pageable pageable);
    
    public Page<Event> findByBasePriceBetween(int startBasePrice, int endBasePrice, Pageable pageable);
    public Page<Event> findByEventStatus(EventStatus eventStatus, Pageable pageable);

    public Page<Event> findByBasePriceBetweenAndEventStatusIs(int startBasePrice, int endBasePrice, EventStatus eventStatus, Pageable pageable);

}
