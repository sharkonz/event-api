package com.alfabet.eventapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alfabet.eventapi.model.Event;
import com.alfabet.eventapi.service.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@PostMapping("/events")
	public ResponseEntity<Event> createEvent(@RequestBody Event event) {
		Event createdEvent = eventService.save(event);
		return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
	}

	@GetMapping("/events/{id}")
	public ResponseEntity<Event> getEventById(@PathVariable Long id) {
		Optional<Event> eventOptional = eventService.findById(id);
		if (eventOptional.isPresent()) {
			return new ResponseEntity<>(eventOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/events/getallevents")
	public ResponseEntity<List<Event>> getAllEvents(@RequestParam(required = false) String location,
			@RequestParam(required = false) String sort, @RequestParam(defaultValue = "asc") String sortOrder) {
		List<Event> events = eventService.findAll(location, sort, sortOrder);
		return ResponseEntity.ok(events);
	}

	@PutMapping("/events/{id}")
	public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
		try {
			Event updatedEvent = eventService.updateEvent(id, eventDetails);
			return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/events/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
		eventService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/batch")
	public ResponseEntity<List<Event>> createEvents(@RequestBody List<Event> events) {
		List<Event> createdEvents = eventService.saveAll(events);
		return new ResponseEntity<>(createdEvents, HttpStatus.CREATED);
	}

	@PutMapping("/batch")
	public ResponseEntity<List<Event>> updateEvents(@RequestBody List<Event> events) {
		List<Event> updatedEvents = eventService.updateAll(events);
		return new ResponseEntity<>(updatedEvents, HttpStatus.OK);
	}

	@DeleteMapping("/batch")
	public ResponseEntity<Void> deleteEvents(@RequestBody List<Long> eventIds) {
		eventService.deleteAllByIds(eventIds);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
