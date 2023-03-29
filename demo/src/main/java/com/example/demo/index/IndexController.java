package com.example.demo.index;

import com.example.demo.events.EventController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//In case you are using HATEOAS v1.0 and above (Spring boot >= 2.2.0), do note that the classnames have changed. Notably the below classes have been renamed:
//
//ResourceSupport changed to RepresentationModel
//Resource changed to EntityModel
//Resources changed to CollectionModel
//PagedResources changed to PagedModel
//ResourceAssembler changed to RepresentationModelAssembler

@RestController
public class IndexController {

    @GetMapping("/api")
    public RepresentationModel index() {
        var index = new RepresentationModel();
        index.add(linkTo(EventController.class).withRel("events"));
        return index;
    }

}
