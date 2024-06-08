package entities;

import helper.Constant;
import helper.StringHelper;
import exceptions.DonationException;
import ui.Main;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Volunteer implements Serializable {
    public static final String VOLUNTEER_CODE = "VL";
    @Serial
    private static final long serialVersionUID = 1L;
    private final String id;
    private final List<Donation> donations = new ArrayList<>();
    private String name;
    private String email;
    private String phone;
    private String password;

    public Volunteer(String id, String name, String email, String phone, String password) {
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.name = name;
    }

    public Volunteer(String name, String email, String phone, String password) {
        this(StringHelper.generateId(VOLUNTEER_CODE), name, email, phone, password);
    }

    public void donate(String eventId, double amount) {
        if (eventId == null || eventId.isBlank() || amount <= 0) {
            throw new DonationException("Donation failed.");
        }
        donations.add(new Donation(eventId, amount));
    }

    public List<Donation> getDonationList() {
        return donations;
    }

    public List<Donation> getSpecificDonationList(String eventId) {
        List<Donation> specificDonations = new ArrayList<>();
        for (Donation donation : donations) {
            if (donation.getEventId().equals(eventId)) {
                specificDonations.add(donation);
            }
        }
        return specificDonations;
    }

    public double getTotalSpecificDonations(String eventId) {
        return donations.stream()
                .filter(donation -> donation.getEventId().equals(eventId))
                .mapToDouble(Donation::getAmount)
                .sum();
    }

    public double getTotalDonations() {
        return donations.stream()
                .mapToDouble(Donation::getAmount)
                .sum();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format(
                "Volunteer(id='%s', name='%s', email='%s', phone='%s', " +
                        "password='%s', donations: %d)",
                id, name, email, phone, password, donations.size());
    }

    public static String serialize(Volunteer volunteer) {
        String donations = volunteer.getDonationList().stream()
                .map(Donation::serialize)
                .collect(Collectors.joining(Constant.SPLITTER));
        return volunteer.getId() + Constant.SPLITTER +
                volunteer.getName() + Constant.SPLITTER +
                volunteer.getEmail() + Constant.SPLITTER +
                volunteer.getPhone() + Constant.SPLITTER +
                volunteer.getPassword() + Constant.SPLITTER_DONATION +
                donations;
    }

    public static Volunteer deserialize(String data) {
        String[] splitData = data.split(Constant.SPLITTER_DONATION);
        String volunteerData = splitData[0];
        String donationData = splitData.length > 1 ? splitData[1] : "";

        String[] parts = volunteerData.split(Constant.SPLITTER);
        Volunteer volunteer = new Volunteer(parts[0], parts[1], parts[2], parts[3], parts[4]);

        if (StringHelper.isValidString(donationData)) {
            volunteer.donations.add(Donation.deserialize(donationData));
        }

        if(Main.getVolunteerRepository().getVolunteers().stream().noneMatch(v -> v.getId().equals(volunteer.getId()))){
            Main.getVolunteerRepository().getAllVolunteers().add(volunteer);
        }

        return volunteer;
    }
}
