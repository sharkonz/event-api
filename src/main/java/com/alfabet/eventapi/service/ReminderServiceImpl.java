package com.alfabet.eventapi.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alfabet.eventapi.model.Event;
import com.alfabet.eventapi.repository.EventRepository;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private EventRepository eventRepository;

    private static final Duration REMINDER_THRESHOLD = Duration.ofMinutes(30);

    @Scheduled(fixedRate = 60000) // checks every minute
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
        System.out.println("Reminder! Event " + event.getName() + ", is happening in " + event.getLocation() + ", at " + event.getDate());
    }

}