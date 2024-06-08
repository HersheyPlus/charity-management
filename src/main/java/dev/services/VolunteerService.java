package dev.services;

import dev.entities.Volunteer;

import java.util.Collection;

public interface VolunteerService {
    public Volunteer login(String volunteerEmail, String volunteerPassword);

    public Volunteer register(String volunteerName, String volunteerPassword, String volunteerEmail, String volunteerPhone);

    public Volunteer findVolunteerById(String volunteerId);

    public Volunteer findVolunteerByEmail(String volunteerEmail);

    public Volunteer removeVolunteer(String volunteerId);

    public Volunteer updateVolunteer(String volunteerId, String volunteerName, String volunteerPassword, String volunteerEmail, String volunteerPhone);

    public Volunteer updateVolunteerName(String volunteerId, String volunteerName);

    public Volunteer updateVolunteerPassword(String volunteerId, String volunteerPassword);

    public Volunteer updateVolunteerEmail(String volunteerId, String volunteerEmail);

    public Volunteer updateVolunteerPhone(String volunteerId, String volunteerPhone);

    public Collection<Volunteer> getAllVolunteers();

    public int getVolunteerCount();
}
