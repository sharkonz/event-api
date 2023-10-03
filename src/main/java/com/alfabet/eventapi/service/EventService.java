package com.alfabet.eventapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import com.alfabet.eventapi.model.Event;

public interface EventService {
	Optional<Event> findById(Long id);

	Event save(Event event);

	Event updateEvent(Long id, Event eventDetails);

	List<Event> findAll(@RequestParam(required = false) String location, @RequestParam(required = false) String sort,
			@RequestParam(defaultValue = "asc") String sortOrder);

	void deleteById(Long id);

	List<Event> findEventsByLocation(String location);

	List<Event> findAllEventsByOrderByDate();

	List<Event> findAllEventsByOrderByPopularity();

	List<Event> findAllEventsByOrderByCreationTime();

	List<Event> saveAll(List<Event> events);

	List<Event> updateAll(List<Event> events);

	void deleteAllByIds(List<Long> ids);
}
