package com.alfabet.eventapi.controller;

import java.util.List;

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

import com.alfabet.eventapi.dto.BatchResult;
import com.alfabet.eventapi.model.Event;
import com.alfabet.eventapi.service.EventService;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@PostMapping
	public ResponseEntity<Event> create(@RequestBody Event event) {
		Event createdEvent = eventService.save(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Event> getById(@PathVariable Long id) {
		return eventService.findById(id).map(event -> ResponseEntity.ok(event))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/getallevents")
	public ResponseEntity<List<Event>> getAll(@RequestParam(required = false) String location,
			@RequestParam(required = false) String sort, @RequestParam(defaultValue = "asc") String sortOrder) {

		List<Event> events = eventService.findAll(location, sort, sortOrder);
		return ResponseEntity.ok(events);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event eventDetails) {
		try {
			Event updatedEvent = eventService.updateEvent(id, eventDetails);
			return ResponseEntity.ok(updatedEvent);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		eventService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/batch")
	public ResponseEntity<BatchResult<Event>> batchSaveOrUpdate(@RequestBody List<Event> events) {
		BatchResult<Event> result = eventService.saveOrUpdateAll(events);
		if (result.getFailedOps().isEmpty()) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(result);
		}
	}

	@PostMapping("/delete/batch")
	public ResponseEntity<BatchResult<Long>> batchDelete(@RequestBody List<Long> eventIds) {
		BatchResult<Long> result = eventService.deleteAll(eventIds);
		return ResponseEntity.ok(result);
	}
}
