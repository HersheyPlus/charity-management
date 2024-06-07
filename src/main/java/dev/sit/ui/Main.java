package dev.sit.ui;

import dev.sit.entities.Event;
import dev.sit.entities.Volunteer;
import dev.sit.exceptions.NotFoundException;
import dev.sit.repositories.EventRepository;
import dev.sit.repositories.VolunteerRepository;
import dev.sit.repositories.storage.DatabaseStorage;
import dev.sit.repositories.storage.FileStorage;
import dev.sit.repositories.storage.MemoryStorage;
import dev.sit.repositories.storage.Storage;

import java.io.Console;
import java.util.Scanner;

public class Main {
    private static VolunteerRepository volunteerRepository;
    private static EventRepository eventRepository;

    private static Storage storage;
    private static Volunteer currentVolunteer;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveData));

        start();
    }

    public static void start() {
        initRepositories();
        selectStorage();
        mainUi();
    }

    public static void initRepositories() {
        eventRepository = new EventRepository();
        volunteerRepository = new VolunteerRepository();
    }

    public static void selectStorage() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Select storage type:");
            System.out.println("1. File");
            System.out.println("2. JDBC");
            System.out.println("3. In-memory");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("File storage selected");
                    storage = new FileStorage();
                    break;
                case 2:
                    System.out.println("JDBC storage selected");
                    jdbcStorage();
                    break;
                case 3:
                    System.out.println("In-memory storage selected");
                    storage = new MemoryStorage();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    throw new NotFoundException("Invalid choice. Please try again.");
            }

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            selectStorage();
        }
    }

    private static void jdbcStorage() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Example JDBC URL: jdbc:sqlite:data.db");
        System.out.println("Example JDBC URL: jdbc:mysql://localhost:3306/charity");
        System.out.print("Enter your JDBC URL: ");
        String jdbcUrl = sc.nextLine();
        System.out.print("Enter your JDBC user: ");
        String jdbcUser = sc.nextLine();
        System.out.print("Enter your JDBC password: ");
        String jdbcPassword = sc.nextLine();
        storage = new DatabaseStorage(jdbcUrl, jdbcUser, jdbcPassword);
    }

    public static void mainUi() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----- Welcome to Charity Management System -----");
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Change storage");
            System.out.println("4. Admin menu");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    loginEvent();
                    break;
                case 2:
                    registerEvent();
                    break;
                case 3:
                    saveData();
                    selectStorage();
                    break;
                case 4:
                    adminLogin();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("*** Invalid choice. Please try again.");
            }
        }

    }

    // Admin Login
    public static void adminLogin() {
        Scanner sc = new Scanner(System.in);
        // password
        Console console = System.console();
        System.out.println("Type 'cancel' to go back to the previous menu");

        while (true) {
            System.out.println("-- Administrator Login ---");
            boolean ec = true;
            String email;
            String password;

            do {
                System.out.print("Enter your email: ");
                email = sc.nextLine();
                if (email.equalsIgnoreCase("cancel")) return;
                System.out.print("Enter your password: ");
                password = sc.nextLine();
                if (password.equalsIgnoreCase("cancel")) return;
                if ("admin@gmail.com".equalsIgnoreCase(email) && "admin".equalsIgnoreCase(password)) {
                    adminUi();
                    ec = false;
                } else {
                    System.out.println("*** Invalid email or password. Please try again.");
                }
            } while (ec);
        }
    }

    //Admin Menu
    public static void adminUi() {
        Scanner sc = new Scanner(System.in);
        System.out.println("-- Administrator Menu --");
        while (true) {
            System.out.println("1. Manage Events");
            System.out.println("2. Manage Volunteers");
            System.out.println("3. List Events");
            System.out.println("4. List Volunteers");
            System.out.println("5. Force save");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    eventsMenu();
                    break;
                case 2:
                    volunteersMenu();
                    break;
                case 3:
                    showAllEvents();
                    break;
                case 4:
                    showAllVolunteers();
                    break;
                case 5:
                    if (storage != null) {
                        System.out.println("Saving data...");
                        storage.saveData();
                    }
                case 6:
                    System.out.println("Exiting...");
                    mainUi();
                    break;
                default:
                    System.out.println("*** Invalid choice. Please try again.");
            }
        }
    }

    // Manage event menu (ADMIN)
    public static void eventsMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("-- Manage Events Menu--");
            System.out.println("1. Create Event");
            System.out.println("2. Delete Event");
            System.out.println("3. previous");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    createEvent();
                    break;
                case 2:
                    deleteEvent();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("*** Invalid choice. Please try again.");
            }
        }
    }

    // Manage volunteer menu (ADMIN)
    public static void volunteersMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("-- Manage Volunteer Menu--");
            System.out.println("1. Change password");
            System.out.println("2. Change name");
            System.out.println("3. Change email");
            System.out.println("4. Change phone");
            System.out.println("5. Delete volunteer");
            System.out.println("6. previous");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    changePassword();
                case 2:
                    changeName();
                case 3:
                    changeEmail();
                case 4:
                    changePhone();
                case 5:
                    deleteVolunteer();
                case 6:
                    return;
                default:
                    System.out.println("*** Invalid choice. Please try again.");
            }
        }
    }

    // create Event (ADMIN)
    public static void createEvent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type 'cancel' to go back to the previous menu");
        while (true) {
            System.out.println("-- Create Event ---");

            String name;
            do {
                System.out.print("Enter event name: ");
                name = sc.nextLine();
                if (name.equalsIgnoreCase("cancel")) return;
                if (name.isEmpty()) {
                    System.out.println("Event name cannot be empty.");
                }
            } while (name.isEmpty());

            String description;
            do {
                System.out.print("Enter event description: ");
                description = sc.nextLine();
                if (description.equalsIgnoreCase("cancel")) return;
                if (description.isEmpty()) {
                    System.out.println("Event description cannot be empty.");
                }
            } while (description.isEmpty());

            String location;
            do {
                System.out.print("Enter event location: ");
                location = sc.nextLine();
                if (location.equalsIgnoreCase("cancel")) return;
                if (location.isEmpty()) {
                    System.out.println("Event location cannot be empty.");
                }
            } while (location.isEmpty());

            String date;
            do {
                System.out.print("Enter event date (dd/MM/yyyy): ");
                date = sc.nextLine();
                if (date.equalsIgnoreCase("cancel")) return;
                if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    System.out.println("Invalid date format. Please use dd/MM/yyyy format.");
                }
            } while (!date.matches("\\d{2}/\\d{2}/\\d{4}"));

            Event event = eventRepository.createEvent(name, description, location, date);

            System.out.println();
            System.out.println("Event created successfully.");
            System.out.println(event);
            System.out.println();

            System.out.println("Do you want to create another event? (Y/N)");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("N")) {
                adminUi();
            } else if (choice.equalsIgnoreCase("Y")) {
                createEvent();
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // delete Event (ADMIN)
    public static void deleteEvent() {
        showAllEvents();
        String evID;
        Scanner sc = new Scanner(System.in);
        System.out.println("Type 'cancel' to go back to the previous menu");

        do {
            System.out.print("Enter Event ID: ");
            evID = sc.nextLine();
            if (evID.equalsIgnoreCase("cancel")) return;
            if (eventRepository.findEvent(evID) == null) {
                System.out.println("There is no such event ID in the system. Please try again");
            }
        } while (eventRepository.findEvent(evID) == null);
        System.out.println("Do you want to delete" + eventRepository.findEvent(evID) + " (Y/N)");

        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("N")) {
            adminUi();
        } else if (choice.equalsIgnoreCase("Y")) {
            System.out.println("Delete " + evID + " successfully");
            eventRepository.removeEvent(evID);
            adminUi();
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    // Manage Volunteer (ADMIN)
    // Change password
    public static void changePassword() {
        showAllVolunteers();
        Scanner sc = new Scanner(System.in);
        String vlID;
        String newPassword;
        System.out.println("Type 'cancel' to go back to the previous menu");
        do {
            System.out.print("Enter Volunteer ID: ");
            vlID = sc.nextLine();
            if (vlID.equalsIgnoreCase("cancel")) return;
            if (volunteerRepository.findVolunteerById(vlID) == null) {
                System.out.println("There is no such event ID in the system. Please try again");
            }
            System.out.print("Enter new password: ");
            newPassword = sc.nextLine();
            volunteerRepository.updateVolunteerPassword(vlID, newPassword);
            System.out.println("Change password id " + vlID + " successfully.");
            volunteersMenu();
        } while (volunteerRepository.findVolunteerById(vlID) == null);
    }

    // Change name
    public static void changeName() {
        showAllVolunteers();
        Scanner sc = new Scanner(System.in);
        String vlID;
        String newName;
        System.out.println("Type 'cancel' to go back to the previous menu");
        do {
            System.out.print("Enter Volunteer ID: ");
            vlID = sc.nextLine();
            if (vlID.equalsIgnoreCase("cancel")) return;
            if (volunteerRepository.findVolunteerById(vlID) == null) {
                System.out.println("There is no such event ID in the system. Please try again");
            }
            System.out.print("Enter new Name: ");
            newName = sc.nextLine();
            volunteerRepository.updateVolunteerName(vlID, newName);
            System.out.println("Change Name id " + vlID + " successfully.");
            volunteersMenu();
        } while (volunteerRepository.findVolunteerById(vlID) == null);
    }

    // Change Email
    public static void changeEmail() {
        showAllVolunteers();
        Scanner sc = new Scanner(System.in);
        String vlID;
        String newEmail;
        System.out.println("Type 'cancel' to go back to the previous menu");
        do {
            System.out.print("Enter Volunteer ID: ");
            vlID = sc.nextLine();
            if (vlID.equalsIgnoreCase("cancel")) return;
            if (volunteerRepository.findVolunteerById(vlID) == null) {
                System.out.println("There is no such event ID in the system. Please try again");
            }
            System.out.print("Enter new Email: ");
            newEmail = sc.nextLine();
            volunteerRepository.updateVolunteerEmail(vlID, newEmail);
            System.out.println("Change email id " + vlID + " successfully.");
            volunteersMenu();
        } while (volunteerRepository.findVolunteerById(vlID) == null);
    }

    //Change phone
    public static void changePhone() {
        showAllVolunteers();
        Scanner sc = new Scanner(System.in);
        String vlID;
        String newPhone;
        System.out.println("Type 'cancel' to go back to the previous menu");
        do {
            System.out.print("Enter Volunteer ID: ");
            vlID = sc.nextLine();
            if (vlID.equalsIgnoreCase("cancel")) return;
            if (volunteerRepository.findVolunteerById(vlID) == null) {
                System.out.println("There is no such event ID in the system. Please try again");
            }
            System.out.print("Enter new password: ");
            newPhone = sc.nextLine();
            volunteerRepository.updateVolunteerPhone(vlID, newPhone);
            System.out.println("Change phone id " + vlID + " successfully.");
            volunteersMenu();
        } while (volunteerRepository.findVolunteerById(vlID) == null);
    }

    // Delete volunteer by id
    public static void deleteVolunteer() {
        showAllVolunteers();
        String vtID;
        Scanner sc = new Scanner(System.in);
        System.out.println("Type 'cancel' to go back to the previous menu");

        do {
            System.out.print("Enter Volunteer ID: ");
            vtID = sc.nextLine();
            if (vtID.equalsIgnoreCase("cancel")) return;
            if (volunteerRepository.findVolunteerById(vtID) == null) {
                System.out.println("There is no such event ID in the system. Please try again");
            }
        } while (volunteerRepository.findVolunteerById(vtID) == null);
        System.out.print("Do you want to delete " + volunteerRepository.findVolunteerById(vtID) + " (Y/N): ");

        String choice = sc.nextLine();

        if (choice.equalsIgnoreCase("N")) {
            adminUi();
        } else if (choice.equalsIgnoreCase("Y")) {
            System.out.println("Delete volunteer id " + vtID + " successfully");
            volunteerRepository.removeVolunteer(vtID);
            adminUi();
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    // Volunteer menu (USER)
    public static void postLoginUI() {
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("----- Post-Login Menu -----");
            System.out.println("1. List Events");
            System.out.println("2. Join Events");
            System.out.println("3. Donate");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    listEventUI();
                    break;
                case 2:
                    joinEvent();
                    break;
                case 3:
                    donateEvent();
                    break;
                case 4:
                    isRunning = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // View Event (USER)
    public static void listEventUI() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            System.out.println("-- List Events --");
            System.out.println("1. Sort by name");
            System.out.println("2. Sort by total donation");
            System.out.println("3. Sort by total volunteer");
            System.out.println("4. previous");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    eventSortByName();
                    break;
                case 2:
                    eventSortByTotalDonation();
                    break;
                case 3:
                    eventSortByTotalVolunteer();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("*** Invalid choice. Please try again.");
            }
        }
    }

    // Join Event (USER)
    public static void joinEvent() {
        Scanner sc = new Scanner(System.in);
        String evID;
        showAllEvents();
        while (true) {
            System.out.println("-- Join Event ---");
            System.out.println("Type 'cancel' to go back to the previous menu");
            System.out.print("Enter Event ID: ");
            evID = sc.nextLine();
            if (evID.equalsIgnoreCase("cancel")) return;
            if (eventRepository.findEvent(evID) == null) {
                System.out.println("There is no such event ID in the system. Please try again");
            } else {
                eventRepository.findEvent(evID).addVolunteer(currentVolunteer);
                System.out.println("Join event " + evID + " successfully");
                break;
            }
        }
    }

    // Donate Event (USER)
    public static void donateEvent() {
        Scanner sc = new Scanner(System.in);
        String evID;
        double amount;
        showAllEvents();
        while (true) {
            System.out.println("-- Donate Event ---");
            System.out.println("Type 'cancel' to go back to the previous menu");
            System.out.print("Enter Event ID: ");
            evID = sc.nextLine();
            if (evID.equalsIgnoreCase("cancel")) return;
            if (eventRepository.findEvent(evID) == null) {
                System.out.println("There is no such event ID in the system. Please try again");
            } else {
                System.out.print("Enter donation amount: ");
                amount = sc.nextDouble();
                if (amount <= 0) {
                    System.out.println("Invalid amount. Please try again.");
                } else {
                    Event event = eventRepository.findEvent(evID);
                    if (event == null) {
                        System.out.println("Event not found");
                        return;
                    }

                    if (!event.isVolunteerExist(currentVolunteer.getId())) {
                        System.out.println("You have not joined this event. Please join the event first.");
                        return;
                    }

                    currentVolunteer.donate(evID, amount);
                    System.out.println("Donate " + amount + " to event " + evID + " successfully");
                    break;
                }
            }
        }
    }

    // Login (USER)
    public static void loginEvent() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("-- Login ---");
            System.out.println("Type 'cancel' to go back to the previous menu");

            String email;
            do {
                System.out.print("Enter your email: ");
                email = sc.nextLine();
                if (email.equals("cancel")) return;
                if (email.isEmpty()) {
                    System.out.println("Email cannot be empty.");
                }
            } while (email.isEmpty());

            String password;
            do {
                System.out.print("Enter your password: ");
                password = sc.nextLine();
                if (password.equals("cancel")) return;
                if (password.isEmpty()) {
                    System.out.println("Password cannot be empty.");
                }
            } while (password.isEmpty());

            Volunteer volunteer = volunteerRepository.login(email, password);

            if (volunteer != null) {
                System.out.println("Login successful.");
                currentVolunteer = volunteer;
                postLoginUI();
                break;
            } else {
                System.out.println("*** Invalid email or password. Please try again.");
            }
        }
    }

    // Register
    public static void registerEvent() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("-- Register ---");
            System.out.println("Type 'cancel' to go back to the previous menu");

            String name;
            do {
                System.out.print("Enter your name: ");
                name = sc.nextLine();
                if (name.equals("cancel")) return;
                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty.");
                }
            } while (name.isEmpty());

            String email;
            do {
                System.out.print("Enter your email: ");
                email = sc.nextLine();
                if (email.equals("cancel")) return;
                if (email.isEmpty()) {
                    System.out.println("Email cannot be empty.");
                }
            } while (email.isEmpty());

            String phone;
            do {
                System.out.print("Enter your phone number: ");
                phone = sc.nextLine();
                if (phone.equals("cancel")) return;
                if (phone.isEmpty()) {
                    System.out.println("Phone number cannot be empty.");
                } else if (!phone.matches("\\d{10}")) {  // Assuming phone number should be 10 digits
                    System.out.println("Invalid phone number. It should be 10 digits.");
                    phone = "";
                }
            } while (phone.isEmpty());

            String password;
            do {
                System.out.print("Enter your password: ");
                password = sc.nextLine();
                if (password.equals("cancel")) return;
                if (password.isEmpty()) {
                    System.out.println("Password cannot be empty.");
                }
            } while (password.isEmpty());

            Volunteer volunteer = volunteerRepository.register(name, password, email, phone);
            if (volunteer == null) {
                System.out.println("Error");
            }

            mainUi();
            break;
        }
    }

    // global_
    public static void showAllVolunteers() {
        if (!volunteerRepository.getVolunteers().isEmpty()) {
            System.out.printf("----- All Volunteers (%d) -----%n", volunteerRepository.getVolunteerCount());
            volunteerRepository.getAllVolunteers().forEach(System.out::println);
            System.out.printf("----- All Volunteers (%d) -----%n", volunteerRepository.getVolunteerCount());
        } else {
            System.out.println("----- All Volunteers (0) -----");
        }

    }

    public static void showAllEvents() {
        if (!eventRepository.getAllEvents().isEmpty()) {
            System.out.printf("----- Available Events (%d) -----%n", eventRepository.getEventCount());
            eventRepository.getAllEvents().forEach(System.out::println);
            System.out.printf("----- Available Events (%d) -----%n", eventRepository.getEventCount());
        } else {
            System.out.println("----- No Event (0) -----");
        }

    }

    public static void eventSortByName() {
        int i = 1;
        System.out.println("-> Sort event By Name");
        for (Event event : eventRepository.sortEvents("name")) {
            System.out.println(i + ". " + event);
            i++;
        }
    }

    public static void eventSortByTotalDonation() {
        int i = 1;
        System.out.println("-> Sort event By Total Donation");
        for (Event event : eventRepository.sortEvents("donation")) {
            System.out.println(i + ". " + event);
            i++;
        }
    }

    public static void eventSortByTotalVolunteer() {
        int i = 1;
        System.out.println("-> Sort event By Total Volunteer");
        for (Event event : eventRepository.sortEvents("volunteer")) {
            System.out.println(i + ". " + event);
            i++;
        }
    }

    private static void saveData() {
        System.out.println("Saving data...");
        if (storage != null) storage.saveData();
    }

    public static EventRepository getEventRepository() {
        return eventRepository;
    }

    public static VolunteerRepository getVolunteerRepository() {
        return volunteerRepository;
    }
}

