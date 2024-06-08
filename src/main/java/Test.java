import dev.entities.Event;
import dev.entities.Volunteer;
import dev.repositories.EventRepository;
import dev.repositories.VolunteerRepository;

public class Test {
    private final static VolunteerRepository volunteerService = new VolunteerRepository();
    private final static EventRepository eventService = new EventRepository();

    public static void main(String[] args) {
        volunteerService.register("John Doe", "12345", "john@gmail.com", "0622509851");
        volunteerService.register("Bombedy", "password123", "bom@gmail.com", "51511");
        volunteerService.register("Lion U", "55555", "lion@gmail.com", "5163161");

        eventService.createEvent("Zombie", "Description 1", "Location 1", "08/11/2021");
        eventService.createEvent("Huggry", "Description 2", "Location 2", "29/08/2023");
        eventService.createEvent("Antery", "Description 3", "Location 3", "08/04/2026");

        Volunteer v1 = volunteerService.findVolunteerById("VL1");
        Volunteer v2 = volunteerService.findVolunteerById("VL2");
        Volunteer v3 = volunteerService.findVolunteerById("VL3");

//        eventService.findEvent("EV1").addVolunteer(v1);
//        eventService.findEvent("EV1").addVolunteer(v2);
//
//        eventService.findEvent("EV2").addVolunteer(v1);
//
//        eventService.findEvent("EV3").addVolunteer(v1);
//        eventService.findEvent("EV3").addVolunteer(v2);
//        eventService.findEvent("EV3").addVolunteer(v3);

        System.out.println("########### TEST #############");
        System.out.println(volunteerService.getVolunteers());
        System.out.println("########### TEST #############");

        System.out.println("-> All events");
        System.out.println(eventService.getAllEvents());
        System.out.println("######################################################");
        System.out.println("-> Event 1 :");
        System.out.println(eventService.findEvent("EV1"));
        System.out.println("Volunteers:: " + eventService.findEvent("EV1").getVolunteerList());
        System.out.println("######################################################");
        System.out.println("-> Event 2 :");
        System.out.println(eventService.findEvent("EV2"));
        System.out.println("Volunteers:: " + eventService.findEvent("EV2").getVolunteerList());
        System.out.println("######################################################");
        System.out.println("-> Event 3 :");
        System.out.println(eventService.findEvent("EV3"));
        System.out.println("Volunteers:: " + eventService.findEvent("EV3").getVolunteerList());
        System.out.println("######################################################");
        System.out.println("-> Donate");
        v1.donate("EV1", 1000);
        v1.donate("EV1", 2500);
        v1.donate("EV2", 9000);
        v2.donate("EV3", 500);
        v2.donate("EV1", 7800);
        v3.donate("EV3", 100000);
        System.out.println("######################################################");
        System.out.println("-> All events (After donated)");
        System.out.println(eventService.getAllEvents());
        System.out.println("######################################################");
        System.out.println("-> Event 1 :");
        System.out.println(eventService.findEvent("EV1"));
        System.out.println("Donations:: " + eventService.findEvent("EV1").getTotalDonations());
        System.out.println("######################################################");
        System.out.println("-> Event 2 :");
        System.out.println(eventService.findEvent("EV2"));
        System.out.println("Donations:: " + eventService.findEvent("EV2").getTotalDonations());
        System.out.println("######################################################");
        System.out.println("-> Event 3 :");
        System.out.println(eventService.findEvent("EV3"));
        System.out.println("Donations:: " + eventService.findEvent("EV3").getTotalDonations());
        System.out.println("######################################################");
        System.out.println("-> Update events (After donated)");
        eventService.updateEvent("EV1", "Zewy Event", "Description 1", "Location 1", "08/11/2021");
        System.out.println("-> Event 1 :");
        System.out.println(eventService.findEvent("EV1"));
        System.out.println("Donations:: " + eventService.findEvent("EV1").getTotalDonations());
        System.out.println("######################################################");
        System.out.println("-> Event 3 :");
        System.out.println(eventService.findEvent("EV3"));
        System.out.println("Donations:: " + eventService.findEvent("EV3").getTotalDonations());
        System.out.println("######################################################");
        System.out.println("-> Event no sort");
        int i = 1;
        for (Event event : eventService.getAllEvents()) {
            System.out.println(i + ". " + event);
            i++;
        }
        i = 1;
        System.out.println("######################################################");
        System.out.println("-> Sort event By Name");
        for (Event event : eventService.sortEvents("name")) {
            System.out.println(i + ". " + event);
            i++;
        }
        i = 1;
        System.out.println("######################################################");
        System.out.println("-> Sort event By Name (reverse)");
        for (Event event : eventService.sortEvents("name", "desc")) {
            System.out.println(i + ". " + event);
            i++;
        }
        i = 1;
        System.out.println("######################################################");
        System.out.println("-> Sort event By Total donation");
        for (Event event : eventService.sortEvents("donation")) {
            System.out.println(i + ". " + event);
            i++;
        }
        i = 1;
        System.out.println("######################################################");
        System.out.println("-> Sort event By Total donation (reverse)");
        for (Event event : eventService.sortEvents("donation", "desc")) {
            System.out.println(i + ". " + event);
            i++;
        }
        i = 1;
        System.out.println("######################################################");
        System.out.println("-> Sort event By Total volunteer");
        for (Event event : eventService.sortEvents("volunteer")) {
            System.out.println(i + ". " + event);
            i++;
        }
        i = 1;
        System.out.println("######################################################");
        System.out.println("-> Sort event By Total volunteer (reverse)");
        for (Event event : eventService.sortEvents("volunteer", "desc")) {
            System.out.println(i + ". " + event);
            i++;
        }
        i = 1;
        System.out.println("######################################################");
        System.out.println("!!! Delete Event 2");
        eventService.removeEvent("EV2");
        System.out.println("-> Event 1 :");
        System.out.println(eventService.findEvent("EV1"));
        System.out.println("Donations:: " + eventService.findEvent("EV1").getTotalDonations());
        System.out.println("######################################################");
        System.out.println("-> Event 3 :");
        System.out.println(eventService.findEvent("EV3"));
        System.out.println("Donations:: " + eventService.findEvent("EV3").getTotalDonations());
        System.out.println("######################################################");
    }
}
