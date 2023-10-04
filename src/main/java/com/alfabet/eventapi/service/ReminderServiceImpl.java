package com.alfabet.eventapi.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alfabet.eventapi.model.Event;
import com.alfabet.eventapi.repository.EventRepository;

@Service
public class ReminderServiceImpl implements ReminderService {

	private final EventRepository eventRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(ReminderServiceImpl.class);
	private static final Duration REMINDER_THRESHOLD = Duration.ofMinutes(30);
	private static final long CHECK_RATE_MILLISECONDS = 60000L;

	@Autowired
	public ReminderServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	/**
	 * Triggers reminders for upcoming events within the REMINDER_THRESHOLD.
	 */
	@Scheduled(fixedRate = CHECK_RATE_MILLISECONDS)
	public void triggerReminders() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime reminderThreshold = now.plus(REMINDER_THRESHOLD);

		List<Event> upcomingEvents = eventRepository.findAllByDateBetween(now, reminderThreshold);

		for (Event event : upcomingEvents) {
			sendReminder(event);
		}
	}

	@Override
	public void sendReminder(Event event) {
		LOGGER.info("Reminder! Event {}, is happening in {}, at {}.", event.getName(), event.getLocation(),
				event.getDate());
	}
}
