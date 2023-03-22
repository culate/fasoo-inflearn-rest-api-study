package com.example.demo.events;

//import static org.assertj.core.api.Assertions.assertThat;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.runners.Parameterized.Parameters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
//import junitparams.Parameters;

public class EventTest {

	@Test
	public void builder() {
		Event event = Event.builder()
				.name("Inflearn Spring REST API")
				.description("REST API development with Spring")
				.build();
		assertThat(event).isNotNull();
	}
	
	@Test
	public void javaBean() {
		String name = "Event";
		String description = "Spring";
		
		Event event = new Event();
		event.setName(name);
		event.setDescription(description);
		
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(description);
	}
	
    @Test
    @Parameters
    public void testFree(/*int basePrice, int maxPrice, boolean isFree*/) {
        // Given
        Event event = Event.builder()
                //.basePrice(basePrice)
                //.maxPrice(maxPrice)
                .basePrice(0)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        //assertThat(event.isFree()).isEqualTo(isFree);
        assertThat(event.isFree()).isTrue();

        // Given
        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        // When
        event.update();

        // Then
        //assertThat(event.isFree()).isEqualTo(isFree);
        assertThat(event.isFree()).isFalse();
}

    @Test
    @Parameters
    public void testOffline(/*String location, boolean isOffline*/) {
        // Given
        Event event = Event.builder()
                //.location(location)
        		.location("강남역 네이버 D2 스타텁 팩토리")
                .build();

        // When
        event.update();

        // Then
        //assertThat(event.isOffline()).isEqualTo(isOffline);
        assertThat(event.isOffline()).isTrue();

        // Given
        event = Event.builder()
                .build();

        // When
        event.update();

        // Then
        //assertThat(event.isOffline()).isEqualTo(isOffline);
        assertThat(event.isOffline()).isFalse();
    }

}
