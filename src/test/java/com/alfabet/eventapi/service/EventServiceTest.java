package com.alfabet.eventapi.service;

import com.alfabet.eventapi.dto.BatchResult;
import com.alfabet.eventapi.model.Event;
import com.alfabet.eventapi.repository.EventRepository;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

	@Mock
	private EventRepository eventRepository;

	@InjectMocks
	private EventServiceImpl eventService;

	@Test
	public void testFindById() {
		Event event = new Event();
		event.setId(1L);
		event.setName("Sample Event");

		when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

		Event foundEvent = eventService.findById(1L).orElse(null);

		assertThat(foundEvent).isNotNull();
		assertThat(foundEvent.getName()).isEqualTo("Sample Event");
	}

	// Test: Save event
	@Test
	public void testSave() {
		// Arrange
		Event event = new Event();
		when(eventRepository.save(event)).thenReturn(event);

		// Act
		Event savedEvent = eventService.save(event);

		// Assert
		assertEquals(savedEvent, event);
	}

	@Test
	public void testFindAll_WithSorting() {
		// Arrange
		List<Event> events = Arrays.asList(new Event(), new Event());

		// Mocking call for sorting by date in descending order
		when(eventRepository.findAllByOrderByDateDesc()).thenReturn(events);

		// Act
		// Here, we're testing the scenario for sorting by date in descending order
		List<Event> result = eventService.findAll(null, "date", "desc");

		// Assert
		assertEquals(result.size(), 2);
		verify(eventRepository, times(1)).findAllByOrderByDateDesc();
	}

	// Test: Find event by ID
	@Test
	public void testFindById_eventExists() {
		// Arrange
		Long eventId = 1L;
		Event event = new Event();
		when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

		// Act
		Optional<Event> result = eventService.findById(eventId);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(result.get(), event);
	}

	@Test
	public void testFindById_eventDoesNotExist() {
		// Arrange
		Long eventId = 1L;
		when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

		// Act
		Optional<Event> result = eventService.findById(eventId);

		// Assert
		assertFalse(result.isPresent());
	}

	// Test: Delete event by ID
	@Test
	public void testDeleteById() {
		// Arrange
		Long eventId = 1L;

		// Act
		eventService.deleteById(eventId);

		// Assert
		verify(eventRepository).deleteById(eventId);
	}

	// Test: Update event
	@Test
	public void testUpdateEvent_eventExists() {
		// Arrange
		Long eventId = 1L;
		Event existingEvent = new Event();
		Event updatedDetails = new Event();
		updatedDetails.setName("Updated Event");
		updatedDetails.setDate(LocalDateTime.now());

		when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
		when(eventRepository.save(existingEvent)).thenReturn(updatedDetails);

		// Act
		Event result = eventService.updateEvent(eventId, updatedDetails);

		// Assert
		assertEquals(result.getName(), updatedDetails.getName());
		assertEquals(result.getDate(), updatedDetails.getDate());
	}

	@Test
	public void testUpdateEvent_eventDoesNotExist() {
		// Arrange
		Long eventId = 1L;
		Event updatedDetails = new Event();

		when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			eventService.updateEvent(eventId, updatedDetails);
		});
	}

	@Test
	public void testFindEventsByLocation() {
		Event event1 = new Event();
		event1.setId(1L);
		event1.setLocation("Location A");

		Event event2 = new Event();
		event2.setId(2L);
		event2.setLocation("Location A");

		List<Event> events = Arrays.asList(event1, event2);

		Mockito.when(eventRepository.findByLocation("Location A")).thenReturn(events);

		List<Event> foundEvents = eventService.findEventsByLocation("Location A");

		assertEquals(2, foundEvents.size());
		assertTrue(foundEvents.stream().allMatch(event -> "Location A".equals(event.getLocation())));
	}

	@Test
	public void testFindAllEventsSortedByDate() {
		Event event1 = new Event();
		event1.setId(1L);
		event1.setDate(LocalDateTime.of(2023, Month.JANUARY, 1, 10, 0));

		Event event2 = new Event();
		event2.setId(2L);
		event2.setDate(LocalDateTime.of(2023, Month.JANUARY, 2, 10, 0));

		List<Event> events = Arrays.asList(event1, event2);

		Mockito.when(eventRepository.findAllByOrderByDateAsc()).thenReturn(events);

		List<Event> foundEvents = eventService.findAllEventsByOrderByDate();

		assertEquals(2, foundEvents.size());
		assertTrue(foundEvents.get(0).getDate().isBefore(foundEvents.get(1).getDate()));
	}

	@Test
	public void testSaveOrUpdateAll() {
		Event event1 = new Event(1L, "EventName1", "EventDescription1", "EventLocation1",
				LocalDateTime.of(2023, 10, 12, 10, 30), LocalDateTime.now(), 100, new HashSet<>(), new HashSet<>());

		Event event2 = new Event(2L, "EventName2", "EventDescription2", "EventLocation2",
				LocalDateTime.of(2023, 11, 12, 12, 30), LocalDateTime.now(), 200, new HashSet<>(), new HashSet<>());

		List<Event> events = Arrays.asList(event1, event2);

		when(eventRepository.saveAll(anyList())).thenReturn(events);

		BatchResult<Event> result = eventService.saveOrUpdateAll(events);

		assertEquals(2, result.getSuccessfulOps().size());
		assertTrue(result.getFailedOps().isEmpty());

		verify(eventRepository, times(1)).saveAll(events);
	}

}
