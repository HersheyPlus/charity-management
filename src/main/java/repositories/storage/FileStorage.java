package repositories.storage;

import entities.Event;
import entities.Volunteer;
import ui.Main;

import java.io.*;

public class FileStorage implements Storage {
    private static final String FILE_NAME = "data.txt";

    public FileStorage() {
        createIfNotExists();
        loadData();
    }

    @Override
    public void loadData() {
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            while (true) {
                Object object = in.readObject();
                if (object instanceof Event event) {
                    Main.getEventRepository().getAllEvents().add(event);
                } else if (object instanceof Volunteer volunteer) {
                    Main.getVolunteerRepository().getAllVolunteers().add(volunteer);
                }
            }
        } catch (EOFException e) {
            // End of file
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void saveData() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            for (Event event : Main.getEventRepository().getAllEvents()) {
                out.writeObject(event);
            }

            for (Volunteer volunteer : Main.getVolunteerRepository().getAllVolunteers()) {
                out.writeObject(volunteer);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void createIfNotExists() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
