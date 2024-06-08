package dev.sit.repositories;

import dev.sit.entities.Event;
import dev.sit.exceptions.EventException;
import dev.sit.services.EventService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static dev.sit.helper.Validation.validateCreateEvent;
import static dev.sit.helper.Validation.validateEventValueBeforeSetValue;


public class EventRepository implements EventService, Serializable {
    private final List<Event> events = new ArrayList<>();

    @Override
    public Event createEvent(String eventName, String location, String description, String eventDateTime) {
        validateCreateEvent(eventName, location, description, eventDateTime);
        Event event = new Event(eventName, location, description, eventDateTime);
        events.add(event);
        return event;
    }

    @Override
    public Event findEvent(String eventId) {
        if (eventId == null || eventId.isBlank()) {
            return null;
        }
        return events.stream().filter(event -> event.getId().equals(eventId)).findFirst().orElse(null);
    }

    @Override
    public Event updateEvent(String eventId, String eventName, String location, String description, String eventDateTime) {
        validateEventValueBeforeSetValue(eventName, "Setting event name failed.");
        validateEventValueBeforeSetValue(description, "Setting event description failed.");
        validateEventValueBeforeSetValue(location, "Setting event location failed.");
        validateEventValueBeforeSetValue(eventDateTime, "Setting event date time failed.");
        Event event = findEvent(eventId);
        if (event != null) {
            event.setName(eventName);
            event.setDescription(description);
            event.setLocation(location);
            event.setDateTime(eventDateTime);
        }
        return event;
    }

    @Override
    public Event removeEvent(String eventId) {
        if (eventId == null || eventId.isBlank()) {
            return null;
        }
        Event event = findEvent(eventId);
        if (event != null) {
            events.remove(event);
        }
        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        return events;
    }

    @Override
    public int getEventCount() {
        return events.size();
    }

    @Override
    public List<Event> sortEvents(String sortBy, String... option) {
        if (sortBy == null || sortBy.isBlank()) {
            throw new EventException("Sorting events failed: Sort by value is required.");
        } else {
            if (sortBy.equalsIgnoreCase("name")) {
                if (option.length > 0 && "desc".equalsIgnoreCase(option[0])) {
                    return events.stream()
                            .sorted(Comparator.comparing(Event::getName).reversed()).collect(Collectors.toList());
                } else {
                    return events.stream()
                            .sorted(Comparator.comparing(Event::getName)).collect(Collectors.toList());
                }
            }
            else if (sortBy.equalsIgnoreCase("volunteer")) {
                if (option.length > 0 && "desc".equalsIgnoreCase(option[0])) {
                    return events.stream()
                            .sorted(Comparator.comparing(Event::getTotalVolunteers).reversed())
                            .collect(Collectors.toList());
                } else {
                    return events.stream()
                            .sorted(Comparator.comparing(Event::getTotalVolunteers))
                            .collect(Collectors.toList());
                }
            }
            else if (sortBy.equalsIgnoreCase("donation")) {
                if (option.length > 0 && "desc".equalsIgnoreCase(option[0])) {
                    return events.stream()
                            .sorted(Comparator.comparing(Event::getTotalDonations).reversed())
                            .collect(Collectors.toList());
                } else {
                    return events.stream()
                            .sorted(Comparator.comparing(Event::getTotalDonations))
                            .collect(Collectors.toList());
                }
            } else {
                return null;
            }
        }
    }
}
