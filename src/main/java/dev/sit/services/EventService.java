package dev.sit.services;

import dev.sit.entities.Event;

import java.util.List;

public interface EventService {
    public Event createEvent(String eventName, String location, String description, String eventDateTime);

    public Event findEvent(String eventId);

    public Event updateEvent(String eventId, String eventName, String location, String description, String eventDateTime);

    public Event removeEvent(String eventId);

    public List<Event> getAllEvents();

    public int getEventCount();

    public List<Event> sortEvents(String sortBy, String... option);
}
