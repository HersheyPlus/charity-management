package entities;

import helper.Constant;
import helper.StringHelper;

import java.io.Serial;
import java.io.Serializable;

public class Donation implements Serializable {
    public static final String DONATION_CODE = "DN";
    @Serial
    private static final long serialVersionUID = 1L;
    private final String id;
    private final double amount;
    private final String eventId;

    public Donation(String id, String eventId, double amount) {
        this.id = id;
        this.amount = amount;
        this.eventId = eventId;
    }

    public Donation(String eventId, double amount) {
        this(StringHelper.generateId(DONATION_CODE), eventId, amount);
    }

    public static String serialize(Donation donation) {
        return donation.getId() + Constant.SPLITTER +
                donation.getEventId() + Constant.SPLITTER +
                donation.getAmount();
    }

    public static Donation deserialize(String data) {
        String[] parts = data.split(Constant.SPLITTER);
        return new Donation(parts[0], parts[1], Double.parseDouble(parts[2]));
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getEventId() {
        return eventId;
    }

    @Override
    public String toString() {
        return String.format("Donation(id: %s, amount: %.2f, eventId: %s, volunteerId: %s)",
                id,
                amount
        );
    }
}
