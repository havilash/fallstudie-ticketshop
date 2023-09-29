package ch.bbcag.backend.ticketshop.util;

import ch.bbcag.backend.ticketshop.event.Event;
import ch.bbcag.backend.ticketshop.person.Person;
import ch.bbcag.backend.ticketshop.ticket.Ticket;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataUtil {
    public static Person getTestPerson() {
        return getTestPersons().get(0);
    }

    public static List<Person> getTestPersons() {
        List<Person> personList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Person person = new Person();
            person.setId(i);
            person.setEmail("person" + i + "@foo.bar");
            person.setPassword("password" + i);
            person.setRoles(new HashSet<>());
            Event event = new Event();

            event.setId(i);
            event.setDate(Date.valueOf("2022-06-04"));
            event.setOwner(person);
            event.setName("Event" + i);
            event.setDescription("Description" + i);
            event.setTickets(new HashSet<>());

            person.setEvents(new HashSet<>());
            person.getEvents().add(event);
            personList.add(person);
        }
        return personList;
    }

    public static Event getTestEvent() {
        return getTestEvents().get(0);
    }

    public static List<Event> getTestEvents() {
        List<Event> events = new ArrayList<>();
        List<Person> persons = getTestPersons();

        for (int i = 1; i <= 4; i++) {
            Event event = new Event();
            event.setId(i);
            event.setOwner(persons.get(i - 1));
            event.setName("Event" + i);
            event.setDescription("Description" + i);
            event.setDate(Date.valueOf("2022-06-04"));

            Set<Ticket> ticketSet = new HashSet<>();
            Ticket ticket = new Ticket();
            ticket.setId(i);
            ticket.setEvent(event);
            ticket.setName("Ticket" + i);
            ticket.setDescription("Description" + i);
            ticket.setAmount(i - 1);
            ticketSet.add(ticket);

            event.setTickets(ticketSet);

            events.add(event);
        }

        return events;
    }

    public static Ticket getTestTicket() {
        return getTestTickets().get(0);
    }

    public static List<Ticket> getTestTickets() {
        List<Ticket> tickets = new ArrayList<>();

        Event event = new Event();
        event.setId(1);
        event.setName("Event1");
        event.setDescription("Description1");
        event.setOwner(getTestPerson());

        for (int i = 1; i <= 4; i++) {
            Ticket ticket = new Ticket();
            ticket.setId(i);
            ticket.setName("Ticket" + i);
            ticket.setDescription("Description" + i);
            ticket.setEvent(event);
            ticket.setAmount(i);
            tickets.add(ticket);
        }

        return tickets;
    }
}
