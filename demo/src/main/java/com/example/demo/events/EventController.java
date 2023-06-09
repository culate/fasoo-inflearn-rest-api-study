package com.example.demo.events;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.accounts.Account;
import com.example.demo.accounts.AccountAdapter;
import com.example.demo.accounts.CurrentUser;
import com.example.demo.common.ErrorsResource;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;
    
    private final EventValidator eventValidator;

    @Autowired 
    ErrorsResource errorsResource;

    public EventController(EventRepository eventRepository, 
    		ModelMapper modelMapper, 
    		EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;
    }

	@PostMapping
	public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
            //return ResponseEntity.badRequest().body(errors);
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
            //return ResponseEntity.badRequest().body(errors);
        }

        Event event = modelMapper.map(eventDto,  Event.class);
        event.update();
        Event newEvent = this.eventRepository.save(event);

        //ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
        //ControllerLinkBuilder -> WebMvcLinkBuilder
        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
		URI createdUri = selfLinkBuilder.toUri();
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        //eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));
        eventResource.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));
        return ResponseEntity.created(createdUri).body(eventResource);
		//return ResponseEntity.created(createUri).body(new EventResource(event));
	}

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable,
                                      PagedResourcesAssembler<Event> assembler,
                                      @CurrentUser Account account,
                                      @RequestParam(required = false, defaultValue = "-1") int startBasePrice,
                                      @RequestParam(required = false, defaultValue = "-1") int endBasePrice,
                                      @RequestParam(required = false) boolean checkEnrollment
                                      ) {

    	Page<Event> page;
//    	if (false) {
//	        Specification<Event> spec = Specification.where(null);
//
//	    	if (startBasePrice >= 0 && endBasePrice >= startBasePrice) {
//	    		spec = spec.and(EventSpecification.limitBasePrice(startBasePrice, endBasePrice));
//	    	}
//    		if (checkEnrollment) {
//    			spec = spec.and(EventSpecification.progressEnrollment());
//    		}
//	        page = this.eventRepository.findAll(spec, pageable);
//	    	
//    	} else {
	    	if (startBasePrice >= 0 && endBasePrice >= startBasePrice) {
	    		if (checkEnrollment) {
	    			page = this.eventRepository.findByBasePriceBetweenAndEventStatusIs(startBasePrice, endBasePrice, EventStatus.BEGAN_ENROLLMEND, pageable);
	    		} else {
	    			page = this.eventRepository.findByBasePriceBetween(startBasePrice, endBasePrice, pageable);
	    		}
	    	} else if (checkEnrollment) {
	    		page = this.eventRepository.findByEventStatus(EventStatus.BEGAN_ENROLLMEND, pageable);
	    	} else {
	    		page = this.eventRepository.findAll(pageable);
	    	}
//    	}
        var pagedResources = assembler.toModel(page, e -> new EventResource(e));
        pagedResources.add(/*new Link*/Link.of("/docs/index.html#resources-events-list").withRel("profile"));
        if (account != null) {
            pagedResources.add(linkTo(EventController.class).withRel("create-event"));
        }
        return ResponseEntity.ok(pagedResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id,
                                   @CurrentUser Account currentUser) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Event event = optionalEvent.get();
        EventResource eventResource = new EventResource(event);
        eventResource.add(/*new Link*/Link.of("/docs/index.html#resources-events-get").withRel("profile"));
        if (event.getManager().equals(currentUser)) {
            eventResource.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
        }

        return ResponseEntity.ok(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id,
                                      @RequestBody @Valid EventDto eventDto,
                                      Errors errors,
                                      @CurrentUser Account currentUser) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        this.eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event existingEvent = optionalEvent.get();
        if (!existingEvent.getManager().equals(currentUser)) {
            return new ResponseEntity(HttpStatus.FORBIDDEN); //UNAUTHORIZED => FORBIDDEN
        }

        this.modelMapper.map(eventDto, existingEvent);
        Event savedEvent = this.eventRepository.save(existingEvent);

        EventResource eventResource = new EventResource(savedEvent);
        eventResource.add(/*new Link*/Link.of("/docs/index.html#resources-events-update").withRel("profile"));

        return ResponseEntity.ok(eventResource);
    }

    private ResponseEntity badRequest(Errors errors) {
    	// https://acet.pe.kr/924
    	// 이 코드가 좋아 보여서 적용해봄. 
    	// 뭐가 더 좋은지는 모르겠고.. 공부 차원..
        //return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    	return ResponseEntity.badRequest().body(errorsResource.addLink(errors));
    }
}
