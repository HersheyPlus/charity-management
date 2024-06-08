package dev.repositories.storage;

import dev.entities.Event;
import dev.entities.Volunteer;
import dev.ui.Main;

import java.sql.*;

public class DatabaseStorage implements Storage {
    private final String jdbcUrl;
    private final String jdbc_user;
    private final String jdbc_password;

    private Connection connection;

    public DatabaseStorage(String url, String user, String password) {
        this.jdbcUrl = url;
        this.jdbc_user = user;
        this.jdbc_password = password;

        createIfNotExists();
        loadData();
    }

    @Override
    public void loadData() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM data");
            while (resultSet.next()) {
                String data = resultSet.getString("data");
                if (data.startsWith(Event.EVENT_CODE)) {
                    Event.deserialize(data);
                }
                if (data.startsWith(Volunteer.VOLUNTEER_CODE)) {
                    Volunteer.deserialize(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveData() {
        clearTable();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO data (data) VALUES (?)")) {
            for (Event event : Main.getEventRepository().getAllEvents()) {
                statement.setString(1, Event.serialize(event));
                statement.addBatch();
            }
            for (Volunteer volunteer : Main.getVolunteerRepository().getAllVolunteers()) {
                statement.setString(1, Volunteer.serialize(volunteer));
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createIfNotExists() {
        try {
            connection = DriverManager.getConnection(jdbcUrl, jdbc_user, jdbc_password);
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS data (data TEXT)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearTable() {
        try {
            connection.createStatement().execute("DELETE FROM data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
