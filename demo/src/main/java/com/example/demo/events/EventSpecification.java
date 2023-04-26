package com.example.demo.events;

import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {
    public static Specification<Event> limitBasePrice(int start, int end) {
        return (Specification<Event>) ((root, query, builder) -> 
                builder.between(root.get("basePrice"), start, end)
        );
    }

    public static Specification<Event> progressEnrollment() {
        return (Specification<Event>) ((root, query, builder) -> 
                builder.equal(root.get("eventStatus"), EventStatus.BEGAN_ENROLLMEND)
        );
    }

}
