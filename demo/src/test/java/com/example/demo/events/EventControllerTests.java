package com.example.demo.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void createEvent() throws Exception {
		mockMvc.perform(post(/*urlTemplate:*/"/api/events/")
				.contentType(MediaType.APPLICATION_JSON) //APPLICATION_JSON_UTF8 deprecated. 
				.accept(MediaTypes.HAL_JSON))
		.andExpect(status().isCreated());
	}
}
