package ch.bbcag.backend.ticketshop.data.dto;

import ch.bbcag.backend.ticketshop.ticket.Ticket;
import ch.bbcag.backend.ticketshop.ticket.TicketDTO;
import ch.bbcag.backend.ticketshop.ticket.TicketMapper;
import ch.bbcag.backend.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static ch.bbcag.backend.ticketshop.util.DataDTOUtil.getTestTicketDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(29)
public class TicketMapperTest {
    @Test
    @Order(1)
    public void checkFromDTO_whenValidTicketDTO_thenTicketIsReturned() {
        TicketDTO testTicketDTO = getTestTicketDTO();

        Ticket expectedTicket = DataUtil.getTestTicket();

        Ticket actualTicket = TicketMapper.fromDTO(testTicketDTO);

        assertEquals(expectedTicket.getId(), actualTicket.getId());
        assertEquals(expectedTicket.getName(), actualTicket.getName());
        assertEquals(expectedTicket.getDescription(), actualTicket.getDescription());
        assertEquals(expectedTicket.getAmount(), actualTicket.getAmount());
        assertEquals(expectedTicket.getEvent().getId(), actualTicket.getEvent().getId());

        assertEquals(expectedTicket, actualTicket);
    }

    @Test
    @Order(2)
    public void checkToDTO_whenValidTicket_thenTicketDTOIsReturned() {
        Ticket testTicket = DataUtil.getTestTicket();

        TicketDTO expectedTicketDTO = getTestTicketDTO();

        TicketDTO actualTicketDTO = TicketMapper.toDTO(testTicket);

        assertEquals(expectedTicketDTO.getId(), actualTicketDTO.getId());
        assertEquals(expectedTicketDTO.getName(), actualTicketDTO.getName());
        assertEquals(expectedTicketDTO.getDescription(), actualTicketDTO.getDescription());
        assertEquals(expectedTicketDTO.getAmountToBuy(), actualTicketDTO.getAmountToBuy());
        assertEquals(expectedTicketDTO.getEventId(), actualTicketDTO.getEventId());

        assertEquals(expectedTicketDTO, actualTicketDTO);
    }
}
