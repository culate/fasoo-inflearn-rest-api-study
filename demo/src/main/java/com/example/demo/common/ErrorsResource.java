package com.example.demo.common;

import com.example.demo.index.IndexController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
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

//public class ErrorsResource extends EntityModel<Errors> {
//
//    public ErrorsResource(Errors content, Link... links) {
//        //super(content, links);
//		super(content, Arrays.asList(links));
//    	//super(content);
//		//this.add(links);
//
//
//        add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
//    }
//
//}

// https://acet.pe.kr/924 에서 쓴 방법
// 이거 쓰면 new가 어케 될까나.. -> EventController 변경
@Component
public class ErrorsResource{
    public EntityModel<Errors> addLink(Errors content) {
        EntityModel<Errors> entityModel = EntityModel.of(content);
        entityModel.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
        return entityModel;
    }
}
