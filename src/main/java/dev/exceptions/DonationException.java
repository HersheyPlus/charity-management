package dev.exceptions;

public class DonationException extends RuntimeException {
    public DonationException(String message) {
        super(message);
    }

    public DonationException(String message, Throwable cause) {
        super(message, cause);
    }
}
