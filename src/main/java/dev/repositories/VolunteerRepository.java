package dev.repositories;

import dev.entities.Volunteer;
import dev.services.VolunteerService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static dev.helper.Validation.validateCreateVolunteer;
import static dev.helper.Validation.validateVolunteerValueBeforeSetValue;

public class VolunteerRepository implements VolunteerService, Serializable {
    private final List<Volunteer> volunteers = new ArrayList<>();

    public List<Volunteer> getVolunteers() {
        return volunteers;
    }

    @Override
    public Volunteer login(String volunteerEmail, String volunteerPassword) {
        Volunteer volunteer = findVolunteerByEmail(volunteerEmail);
        if (volunteer == null) return null;
        if (volunteer.getPassword().equals(volunteerPassword)) return volunteer;

        return null;
    }

    @Override
    public Volunteer register(String volunteerName, String volunteerPassword, String volunteerEmail, String volunteerPhone) {
        validateCreateVolunteer(volunteerName, volunteerPassword, volunteerEmail, volunteerPhone);
        Volunteer volunteer = new Volunteer(volunteerName, volunteerEmail, volunteerPhone, volunteerPassword);
        volunteers.add(volunteer);
        return volunteer;
    }

    @Override
    public Volunteer findVolunteerById(String volunteerId) {
        if (volunteerId == null || volunteerId.isBlank()) {
            return null;
        }
        return volunteers.stream().filter(volunteer -> volunteer.getId().equals(volunteerId)).findFirst().orElse(null);
    }

    @Override
    public Volunteer findVolunteerByEmail(String volunteerEmail) {
        if (volunteerEmail == null || volunteerEmail.isBlank()) {
            return null;
        }
        return volunteers.stream().filter(volunteer -> volunteer.getEmail().equals(volunteerEmail)).findFirst().orElse(null);
    }

    @Override
    public Volunteer removeVolunteer(String volunteerId) {
        if (volunteerId == null || volunteerId.isBlank()) {
            return null;
        }
        Volunteer volunteer = findVolunteerById(volunteerId);
        if (volunteer != null) {
            volunteers.remove(volunteer);
        }
        return volunteer;
    }

    @Override
    public Volunteer updateVolunteer(String volunteerId, String volunteerName, String volunteerPassword, String volunteerEmail, String volunteerPhone) {
        validateCreateVolunteer(volunteerName, volunteerPassword, volunteerEmail, volunteerPhone);
        Volunteer volunteer = findVolunteerById(volunteerId);
        if (volunteer != null) {
            volunteer.setName(volunteerName);
            volunteer.setPassword(volunteerPassword);
            volunteer.setEmail(volunteerEmail);
            volunteer.setPhone(volunteerPhone);
        }
        return volunteer;
    }

    @Override
    public Volunteer updateVolunteerName(String volunteerId, String volunteerName) {
        validateVolunteerValueBeforeSetValue(volunteerName, "Setting volunteer name failed.");
        Volunteer volunteer = findVolunteerById(volunteerId);
        if (volunteer != null) {
            volunteer.setName(volunteerName);
        }
        return volunteer;
    }

    @Override
    public Volunteer updateVolunteerPassword(String volunteerId, String volunteerPassword) {
        validateVolunteerValueBeforeSetValue(volunteerPassword, "Setting volunteer password failed.");
        Volunteer volunteer = findVolunteerById(volunteerId);
        if (volunteer != null) {
            volunteer.setPassword(volunteerPassword);
        }
        return volunteer;
    }

    @Override
    public Volunteer updateVolunteerEmail(String volunteerId, String volunteerEmail) {
        validateVolunteerValueBeforeSetValue(volunteerEmail, "Setting volunteer email failed.");
        Volunteer volunteer = findVolunteerById(volunteerId);
        if (volunteer != null) {
            volunteer.setEmail(volunteerEmail);
        }
        return volunteer;
    }

    @Override
    public Volunteer updateVolunteerPhone(String volunteerId, String volunteerPhone) {
        validateVolunteerValueBeforeSetValue(volunteerPhone, "Setting volunteer phone failed.");
        Volunteer volunteer = findVolunteerById(volunteerId);
        if (volunteer != null) {
            volunteer.setPhone(volunteerPhone);
        }
        return volunteer;
    }

    @Override
    public Collection<Volunteer> getAllVolunteers() {
        return volunteers;
    }

    @Override
    public int getVolunteerCount() {
        return volunteers.size();
    }
}
