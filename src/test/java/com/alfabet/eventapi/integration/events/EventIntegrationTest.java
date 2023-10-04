package com.alfabet.eventapi.integration.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.alfabet.eventapi.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class EventIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testEventCrudOperations() throws Exception {
		// 1. Create an event.
		Event newEvent = new Event(null, "Test Event", "Description", "Location", LocalDateTime.now(),
				LocalDateTime.now(), 10, null, null);
		String eventJson = objectMapper.writeValueAsString(newEvent);
		String createdEventResponseBody = mockMvc
				.perform(post("/events").contentType(MediaType.APPLICATION_JSON).content(eventJson))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
		Event createdEvent = objectMapper.readValue(createdEventResponseBody, Event.class);

		// 2. Update the event.
		createdEvent.setName("Updated Event Name");
		String updatedEventJson = objectMapper.writeValueAsString(createdEvent);
		mockMvc.perform(put("/events/" + createdEvent.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(updatedEventJson)).andExpect(status().isOk());

		// 3. Add another event.
		Event secondEvent = new Event(null, "Second Event", "Description", "Location", LocalDateTime.now(),
				LocalDateTime.now(), 15, null, null);
		mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(secondEvent))).andExpect(status().isCreated());

		// 4. Retrieve all events and check if both exist.
		mockMvc.perform(get("/events/getallevents")).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].name").value("Updated Event Name"))
				.andExpect(jsonPath("$[1].name").value("Second Event"));

		// 5. Delete one event.
		mockMvc.perform(delete("/events/" + createdEvent.getId())).andExpect(status().isNoContent());

		// 6. Retrieve the deleted event and see if it's deleted.
		mockMvc.perform(get("/events/" + createdEvent.getId())).andExpect(status().isNotFound());
	}
}
