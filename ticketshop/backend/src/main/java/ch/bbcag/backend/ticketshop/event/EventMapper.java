package ch.bbcag.backend.ticketshop.event;

import ch.bbcag.backend.ticketshop.person.Person;
import ch.bbcag.backend.ticketshop.ticket.Ticket;
import ch.bbcag.backend.ticketshop.ticket.TicketRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {
    public static EventDTO toDTO(Event event) {
        EventDTO eventDTO = new EventDTO();

        eventDTO.setId(event.getId());
        eventDTO.setName(event.getName());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setDate(event.getDate());
        if (event.getTickets() != null) {
            List<Integer> ticketIds = event
                    .getTickets()
                    .stream()
                    .map(Ticket::getId)
                    .toList();

            eventDTO.setTicketIds(ticketIds);
        }
        eventDTO.setOwnerId(event.getOwner().getId());

        return eventDTO;
    }

    public static Event fromDTO(EventDTO eventDTO) {
        Event event = new Event();

        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate());
        if (eventDTO.getTicketIds() != null) {
            Set<Ticket> tickets = eventDTO
                    .getTicketIds()
                    .stream()
                    .map(Ticket::new)
                    .collect(Collectors.toSet());

            event.setTickets(tickets);
        }
        if (eventDTO.getOwnerId() != null) {
            event.setOwner(new Person(eventDTO.getOwnerId()));
        }

        return event;
    }

}
