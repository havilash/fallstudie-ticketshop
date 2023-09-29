package ch.bbcag.backend.ticketshop.ticket;

import ch.bbcag.backend.ticketshop.event.Event;

public class TicketMapper {
    public static TicketDTO toDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setId(ticket.getId());
        ticketDTO.setName(ticket.getName());
        ticketDTO.setDescription(ticket.getDescription());
        ticketDTO.setAmountToBuy(ticket.getAmount());
        ticketDTO.setEventId(ticket.getEvent().getId());

        return ticketDTO;
    }

    public static Ticket fromDTO(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();

        ticket.setId(ticketDTO.getId());
        ticket.setName(ticketDTO.getName());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setAmount(ticketDTO.getAmountToBuy());
        if (ticketDTO.getEventId() != null) {
            ticket.setEvent(new Event(ticketDTO.getEventId()));
        }

        return ticket;
    }
}
