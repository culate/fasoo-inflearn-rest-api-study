package com.example.demo.common;

import com.example.demo.index.IndexController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Arrays;

//In case you are using HATEOAS v1.0 and above (Spring boot >= 2.2.0), do note that the classnames have changed. Notably the below classes have been renamed:
//
//ResourceSupport changed to RepresentationModel
//Resource changed to EntityModel
//Resources changed to CollectionModel
//PagedResources changed to PagedModel
//ResourceAssembler changed to RepresentationModelAssembler

public class ErrorsResource extends EntityModel<Errors> {

    public ErrorsResource(Errors content, Link... links) {
        //super(content, links);
		super(content, Arrays.asList(links));

        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
    }

}
