package dev.entities;

import dev.helper.Constant;
import dev.helper.StringHelper;
import dev.helper.Validation;
import dev.exceptions.EventException;
import dev.ui.Main;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Event implements Serializable {
    public static final String EVENT_CODE = "EV";
    @Serial
    private static final long serialVersionUID = 1L;
    private final String id;
    private final List<Volunteer> volunteers = new ArrayList<>();
    private String name;
    private String description;
    private String location;
    private LocalDateTime dateTime;

    public Event(String id, String name, String location, String description, String dateTime) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.name = name;
        this.setDateTime(dateTime);
    }

    public Event(String name, String location, String description, String dateTime) {
        this(StringHelper.generateId(EVENT_CODE), name, location, description, dateTime);
    }

    public static String serialize(Event event) {
        String volunteers = event.getVolunteers().stream()
                .map(Volunteer::serialize)
                .collect(Collectors.joining(Constant.SPLITTER));
        return event.getId() + Constant.SPLITTER + event.getName() + Constant.SPLITTER + event.getLocation() +
                Constant.SPLITTER + event.getDescription() + Constant.SPLITTER + event.getDateTimeString() +
                Constant.SPLITTER_VOLUNTEER + volunteers;
    }

    public static Event deserialize(String data) {
        String[] splitData = data.split(Constant.SPLITTER_VOLUNTEER);
        String eventData = splitData[0];
        String volunteerData = splitData.length > 1 ? splitData[1] : "";

        String[] parts = eventData.split(Constant.SPLITTER);
        Event event = new Event(parts[0], parts[1], parts[2], parts[3], parts[4]);

        if (StringHelper.isValidString(volunteerData)) {
            event.volunteers.add(Volunteer.deserialize(volunteerData));
        }

        if(Main.getEventRepository().getAllEvents().stream().noneMatch(e -> e.getId().equals(event.getId()))){
            Main.getEventRepository().getAllEvents().add(event);
        }

        return event;
    }

    public void addVolunteer(Volunteer volunteer) {
        if (volunteer == null) {
            throw new EventException("Adding volunteer failed.");
        }

        if (isVolunteerExist(volunteer.getId())) {
            throw new EventException("Adding volunteer failed: Volunteer already exists.");
        }

        volunteers.add(volunteer);
    }

    public List<String> getVolunteerList() {
        return volunteers.stream()
                .map(volunteer -> String.format("{name: '%s', email: '%s', phone: '%s'}",
                        volunteer.getName(), volunteer.getEmail(), volunteer.getPhone()))
                .collect(Collectors.toList());
    }

    public double getTotalDonations() {
        return volunteers.stream()
                .mapToDouble(volunteers -> volunteers.getTotalSpecificDonations(id))
                .sum();
    }

    public int getTotalVolunteers() {
        return volunteers.size();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Validation.validateEventValueBeforeSetValue(name, "Setting event name failed.");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        Validation.validateEventValueBeforeSetValue(description, "Setting event description failed.");
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        Validation.validateEventValueBeforeSetValue(location, "Setting event location failed.");
        this.location = location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        Validation.validateEventValueBeforeSetValue(dateTime, "Setting event date and time failed.");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        try {
            this.dateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            try {
                LocalDate localDate = LocalDate.parse(dateTime, Constant.DATE_TIME_FORMATTER);
                this.dateTime = localDate.atStartOfDay();
            } catch (DateTimeParseException ex) {
                throw new EventException("Invalid date time format. Please input date time in yyyy-MM-dd'T'HH:mm or dd/MM/yyyy format.");
            }
        }
    }

    public String getDateTimeString() {
        return dateTime.format(Constant.DATE_TIME_FORMATTER);
    }

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    public boolean isVolunteerExist(String volunteerId) {
        return volunteers.stream().anyMatch(v -> v.getId().equals(volunteerId));
    }

    @Override
    public String toString() {
        return String.format("Event(id: '%s', name: '%s', location: '%s', description: '%s', date time: '%s', " +
                        "volunteers: %d, total donations: %.2f)",
                id, name, location, description, dateTime.format(Constant.DATE_TIME_FORMATTER),
                volunteers.size(), getTotalDonations());
    }
}
