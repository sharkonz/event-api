package com.alfabet.eventapi.integration.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alfabet.eventapi.model.Event;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventManagementIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	// Helper method to create a sample event
	private Event createSampleEvent() {
		return new Event(null, "Sample Event", "Sample Description", "Sample Location", LocalDateTime.now().plusDays(1),
				LocalDateTime.now(), 10, null, null);
	}

	// 1.a Schedule a New Event
	@Test
	public void testScheduleANewEvent() {
		Event newEvent = createSampleEvent();
		ResponseEntity<Event> response = restTemplate.postForEntity("http://localhost:" + port + "/events/events",
				newEvent, Event.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().getName()).isEqualTo(newEvent.getName());
		assertThat(response.getBody().getDescription()).isEqualTo(newEvent.getDescription());
		assertThat(response.getBody().getLocation()).isEqualTo(newEvent.getLocation());
		assertThat(response.getBody().getDate()).isEqualTo(newEvent.getDate());
		assertThat(response.getBody().getCreationTime()).isNotNull();
		assertThat(response.getBody().getPopularity()).isEqualTo(newEvent.getPopularity());
	}
}
