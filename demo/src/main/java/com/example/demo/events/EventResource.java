package com.example.demo.events;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Arrays;

//In case you are using HATEOAS v1.0 and above (Spring boot >= 2.2.0), do note that the classnames have changed. Notably the below classes have been renamed:
//
//ResourceSupport changed to RepresentationModel
//Resource changed to EntityModel
//Resources changed to CollectionModel
//PagedResources changed to PagedModel
//ResourceAssembler changed to RepresentationModelAssembler

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class EventResource extends EntityModel<Event> {

	public EventResource(Event event, Link... links) {
        //super(event, links);
		super(event, Arrays.asList(links));
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

}

//BeanSerializer
//public class EventResource extends EntityModel<Event> {
//
//	@JsonUnwrapped
//	private Event event;
//	
//	public EventResource(Event event) {
//		this.event = event;
//    }
//
//	public Event getEvent() {
//		return event;
//	}
//}
