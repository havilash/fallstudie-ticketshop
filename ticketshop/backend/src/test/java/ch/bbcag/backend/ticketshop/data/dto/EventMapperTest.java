package ch.bbcag.backend.ticketshop.data.dto;

import ch.bbcag.backend.ticketshop.event.Event;
import ch.bbcag.backend.ticketshop.event.EventDTO;
import ch.bbcag.backend.ticketshop.event.EventMapper;
import ch.bbcag.backend.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static ch.bbcag.backend.ticketshop.util.DataDTOUtil.getTestEventDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(28)
public class EventMapperTest {
    @Test
    @Order(2)
    public void checkToDTO_whenValidEvent_thenEventDTOIsReturned() {
        Event testEvent = DataUtil.getTestEvent();

        EventDTO expected = getTestEventDTO();

        EventDTO actual = EventMapper.toDTO(testEvent);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getOwnerId(), actual.getOwnerId());
        assertEquals(expected.getTicketIds().size(), actual.getTicketIds().size());

        for (int i = 0; i < expected.getTicketIds().size(); i++) {
            assertEquals(expected.getTicketIds().get(i), actual.getTicketIds().get(i));
        }

        assertEquals(expected, actual);
    }

    @Test
    @Order(1)
    public void checkFromDTO_whenValidEventDTO_thenEventIsReturned() {
        EventDTO testEventDTO = getTestEventDTO();

        Event expected = DataUtil.getTestEvent();

        Event actual = EventMapper.fromDTO(testEventDTO);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getOwner().getId(), actual.getOwner().getId());
        assertEquals(expected.getTickets().size(), actual.getTickets().size());

        for (int i = 0; i < expected.getTickets().size(); i++) {
            assertEquals(expected.getTickets().stream().toList().get(i).getId(), actual.getTickets().stream().toList().get(i).getId());
        }

        assertEquals(expected, actual);
    }
}
