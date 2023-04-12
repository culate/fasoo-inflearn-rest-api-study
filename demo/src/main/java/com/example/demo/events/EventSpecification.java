package com.example.demo.events;

import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {
    public static Specification<Event> limitBasePrice() {
        return (Specification<Event>) ((root, query, builder) -> 
                builder.between(root.get("basePrice"), 100, 200)
        );
    }

    public static Specification<Event> progressEnrollment() {
        return (Specification<Event>) ((root, query, builder) -> 
                builder.equal(root.get("eventStatus"), EventStatus.BEGAN_ENROLLMEND)
        );
    }

}
