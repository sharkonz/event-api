package com.alfabet.eventapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.alfabet.eventapi.dto.BatchResult;
import com.alfabet.eventapi.exception.ErrorDetail;
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

	public BatchResult<Event> saveOrUpdateAll(List<Event> events) {
		List<Event> successfulOps = new ArrayList<>();
		List<ErrorDetail<Long>> failedOps = new ArrayList<>();

		try {
			// Phase 1: Try bulk save/update
			successfulOps = eventRepository.saveAll(events);
		} catch (Exception bulkException) {
			// Phase 2: If bulk save/update fails, try one by one
			for (Event event : events) {
				try {
					Event savedEvent = eventRepository.save(event);
					successfulOps.add(savedEvent);
				} catch (Exception individualException) {
					failedOps.add(new ErrorDetail<>(event.getId(), individualException.getMessage()));
				}
			}
		}

		return new BatchResult<>(successfulOps, failedOps);
	}

	@Override
	public BatchResult<Long> deleteAll(List<Long> eventIds) {
	    List<Long> successfulDeletes = new ArrayList<>();
	    List<ErrorDetail<Long>> failedDeletes = new ArrayList<>();

	    try {
	        eventRepository.deleteAllByIds(eventIds);
	        successfulDeletes.addAll(eventIds);
	    } catch (Exception e) {
	        // If batch delete fails, try deleting one by one
	        for (Long id : eventIds) {
	            try {
	                eventRepository.deleteById(id);
	                successfulDeletes.add(id);
	            } catch (Exception individualEx) {
	                failedDeletes.add(new ErrorDetail<>(id, individualEx.getMessage()));
	            }
	        }
	    }
	    return new BatchResult<>(successfulDeletes, failedDeletes);
	}



//	public BatchResult deleteAll(List<Long> eventIds) {
//		List<Event> successfulDeletes = new ArrayList<>();
//		List<ErrorDetail<Long>> failedDeletes = new ArrayList<>();
//
//		for (Long id : eventIds) {
//			try {
//				eventRepository.deleteById(id);
//				// Assuming you want to return successfully deleted events as well
//				// If not, you can skip the next line
//				successfulDeletes.add(new Event(id)); // Create a lightweight event instance just to convey the ID
//			} catch (Exception e) {
//				failedDeletes.add(new ErrorDetail<>(id, e.getMessage()));
//			}
//		}
//
//		return new BatchResult(successfulDeletes, failedDeletes);
//	}
}
