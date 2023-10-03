package com.alfabet.eventapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.alfabet.eventapi.model.Event;
import com.alfabet.eventapi.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	@Override
	public Event save(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public List<Event> findAll(String location, String sort, String sortOrder) {
		// Check if sorting order is ascending or descending
		boolean isAscending = "asc".equalsIgnoreCase(sortOrder);

		if (location != null && !location.isEmpty() && sort != null && !sort.isEmpty()) {
			switch (sort) {
			case "date":
				return isAscending ? eventRepository.findByLocationOrderByDateAsc(location)
						: eventRepository.findByLocationOrderByDateDesc(location);
			case "popularity":
				return isAscending ? eventRepository.findByLocationOrderByPopularityAsc(location)
						: eventRepository.findByLocationOrderByPopularityDesc(location);
			case "creationTime":
				return isAscending ? eventRepository.findByLocationOrderByCreationTimeAsc(location)
						: eventRepository.findByLocationOrderByCreationTimeDesc(location);
			default:
				return eventRepository.findByLocation(location);
			}
		} else if (location != null && !location.isEmpty()) {
			return eventRepository.findByLocation(location);
		} else if (sort != null && !sort.isEmpty()) {
			switch (sort) {
			case "date":
				return isAscending ? eventRepository.findAllByOrderByDateAsc()
						: eventRepository.findAllByOrderByDateDesc();
			case "popularity":
				return isAscending ? eventRepository.findAllByOrderByPopularityAsc()
						: eventRepository.findAllByOrderByPopularityDesc();
			case "creationTime":
				return isAscending ? eventRepository.findAllByOrderByCreationTimeAsc()
						: eventRepository.findAllByOrderByCreationTimeDesc();
			default:
				return eventRepository.findAll();
			}
		}

		return eventRepository.findAll();
	}

	@Override
	public Optional<Event> findById(Long id) {
		return eventRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		eventRepository.deleteById(id);
	}

	@Override
	public Event updateEvent(Long id, Event eventDetails) {
		Optional<Event> eventOptional = eventRepository.findById(id);

		if (eventOptional.isPresent()) {
			Event event = eventOptional.get();

			// Update event attributes here.
			if (eventDetails.getName() != null) {
				event.setName(eventDetails.getName());
			}
			if (eventDetails.getDate() != null) {
				event.setDate(eventDetails.getDate());
			}
			if (eventDetails.getDescription() != null) {
				event.setDescription(eventDetails.getDescription());
			}
			if (eventDetails.getLocation() != null) {
				event.setLocation(eventDetails.getLocation());
			}
			if (eventDetails.getCreationTime() != null) {
				event.setCreationTime(eventDetails.getCreationTime());
			}
			if (eventDetails.getPopularity() >= 0) { // assuming 0 as the minimum
				event.setPopularity(eventDetails.getPopularity());
			}
			if (eventDetails.getTags() != null && !eventDetails.getTags().isEmpty()) {
				event.setTags(eventDetails.getTags());
			}
			if (eventDetails.getSubscribers() != null && !eventDetails.getSubscribers().isEmpty()) {
				event.setSubscribers(eventDetails.getSubscribers());
			}
			return eventRepository.save(event);
		} else {
			throw new ResourceNotFoundException("Event not found with id: " + id);
		}
	}

	@Override
	public List<Event> findEventsByLocation(String location) {
		return eventRepository.findByLocation(location);
	}

	@Override
	public List<Event> findAllEventsByOrderByDate() {
		return eventRepository.findAllByOrderByDateAsc();
	}

	public List<Event> findAllEventsByOrderByPopularity() {
		return eventRepository.findAllByOrderByPopularityAsc();
	}

	public List<Event> findAllEventsByOrderByCreationTime() {
		return eventRepository.findAllByOrderByCreationTimeAsc();
	}

	@Override
	public List<Event> saveAll(List<Event> events) {
		return eventRepository.saveAll(events);
	}

	@Override
	public List<Event> updateAll(List<Event> events) {
		// You'd loop through the events and update them using your logic
		// For simplicity, we'll just use the saveAll method here
		return eventRepository.saveAll(events);
	}

	@Override
	public void deleteAllByIds(List<Long> ids) {
		eventRepository.deleteAllById(ids);
	}
}
