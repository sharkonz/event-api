package com.alfabet.eventapi.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.alfabet.eventapi.model.Event;
import com.alfabet.eventapi.repository.EventRepository;

import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class ReminderServiceTest {

    @InjectMocks
    private ReminderServiceImpl reminderService;

    // Mock the EventRepository if ReminderService interacts with it for checking events
    @Mock
    private EventRepository eventRepository;

    @Test
    public void testTriggerReminders() {
        Event event = new Event();
        event.setId(1L);
        event.setName("Test Event");
        event.setLocation("AlfaBet office");
        event.setDate(LocalDateTime.now().plus(Duration.ofMinutes(20)));

        Mockito.when(eventRepository.findAllByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.singletonList(event));

        reminderService.triggerReminders();

        // Now, we should have printed a reminder for our event.
        // This is tricky to test since we're just printing to the console.
        // A more testable design would involve collecting sent reminders or sending them through another service.
        // For this simplified example, manually verify that the reminder is printed in the console.
    }
}
