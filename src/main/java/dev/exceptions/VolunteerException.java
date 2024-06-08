package dev.exceptions;

public class VolunteerException extends RuntimeException {
    public VolunteerException(String message) {
        super(message);
    }

    public VolunteerException(String message, Throwable cause) {
        super(message, cause);
    }
}
