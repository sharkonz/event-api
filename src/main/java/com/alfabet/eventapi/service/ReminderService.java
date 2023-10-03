package com.alfabet.eventapi.service;

import com.alfabet.eventapi.model.Event;

public interface ReminderService {
    void sendReminder(Event event);
    void triggerReminders();
}
