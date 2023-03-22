package com.example.demo.events;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

//import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.Resources;
//import org.springframework.hateoas.mvc.ControllerLinkBuilder;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import java.net.URI;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;
    
    private final EventValidator eventValidator;
    
    public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;
    }

	@PostMapping
	public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        if (errors.hasErrors()) {
            //return badRequest(errors);
        	return ResponseEntity.badRequest().body(errors);
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
        	return ResponseEntity.badRequest().body(errors);
        }

        Event event = modelMapper.map(eventDto,  Event.class);
        event.update();
        Event newEvent = this.eventRepository.save(event);
		URI createUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
		return ResponseEntity.created(createUri).body(event);
	}
}
