package helper;

import exceptions.DonationException;
import exceptions.EventException;
import exceptions.VolunteerException;

public class Validation {
    public static void validateCreateDonation(String eventId, double amount) {
        if (eventId == null || eventId.isBlank() || amount < 0) {
            throw new DonationException("Creating donation failed.");
        }
    }

    public static void validateCreateVolunteer(String volunteerName, String volunteerEmail, String volunteerPhone, String volunteerPassword) {
        if (volunteerName == null || volunteerPassword == null || volunteerEmail == null || volunteerPhone == null || volunteerName.isBlank() || volunteerEmail.isBlank() || volunteerPhone.isBlank() || volunteerPassword.isBlank()) {
            throw new VolunteerException("Creating volunteer failed.");
        }
    }

    public static void validateVolunteerValueBeforeSetValue(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new VolunteerException(message);
        }
    }

    public static void validateEventValueBeforeSetValue(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new EventException(message);
        }
    }

    public static void validateCreateEvent(String eventName, String location, String description, String eventDateTime) {
        if (eventName == null || description == null || location == null || eventDateTime == null || eventName.isBlank() || description.isBlank() || location.isBlank() || eventDateTime.isBlank()) {
            throw new EventException("Creating event failed.");
        }
    }
}
