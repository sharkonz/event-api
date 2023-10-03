package com.alfabet.eventapi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfabet.eventapi.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	List<Event> findByLocation(String location);

	List<Event> findAllByOrderByDateAsc();

	List<Event> findAllByOrderByPopularityAsc();

	List<Event> findAllByOrderByCreationTimeAsc();

	List<Event> findAllByDateBetween(LocalDateTime start, LocalDateTime end);

	List<Event> findByLocationOrderByDateAsc(String location);
	
	List<Event> findByLocationOrderByDateDesc(String location);

	List<Event> findByLocationOrderByPopularityDesc(String location);

	List<Event> findByLocationOrderByPopularityAsc(String location);
	
	List<Event> findByLocationOrderByCreationTimeAsc(String location);
	
	List<Event> findByLocationOrderByCreationTimeDesc(String location);

	List<Event> findAllByOrderByDateDesc();

	List<Event> findAllByOrderByPopularityDesc();

	List<Event> findAllByOrderByCreationTimeDesc();
}
