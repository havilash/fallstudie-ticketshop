package ch.bbcag.backend.ticketshop.util;

import ch.bbcag.backend.ticketshop.event.EventDTO;
import ch.bbcag.backend.ticketshop.person.PersonRequestDTO;
import ch.bbcag.backend.ticketshop.person.PersonResponseDTO;
import ch.bbcag.backend.ticketshop.ticket.TicketDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DataDTOUtil {
    public static final String JSON_ALL_EVENT_DTOS = "[{\"id\":1,\"name\":\"Event1\",\"ownerId\":1,\"ticketIds\":[1],\"date\":\"2022-06-04\",\"description\":\"Description1\"}," +
            "{\"id\":2,\"name\":\"Event2\",\"ownerId\":2,\"ticketIds\":[2],\"date\":\"2022-06-04\",\"description\":\"Description2\"}," +
            "{\"id\":3,\"name\":\"Event3\",\"ownerId\":3,\"ticketIds\":[3],\"date\":\"2022-06-04\",\"description\":\"Description3\"}," +
            "{\"id\":4,\"name\":\"Event4\",\"ownerId\":4,\"ticketIds\":[4],\"date\":\"2022-06-04\",\"description\":\"Description4\"}]";
    public static final String JSON_ALL_TICKET_DTOS = "[{\"id\":1,\"name\":\"Ticket1\",\"description\":\"Description1\",\"eventId\":1,\"amountToBuy\":1}," +
            "{\"id\":2,\"name\":\"Ticket2\",\"description\":\"Description2\",\"eventId\":1,\"amountToBuy\":2}," +
            "{\"id\":3,\"name\":\"Ticket3\",\"description\":\"Description3\",\"eventId\":1,\"amountToBuy\":3}," +
            "{\"id\":4,\"name\":\"Ticket4\",\"description\":\"Description4\",\"eventId\":1,\"amountToBuy\":4}]";

    public static PersonResponseDTO getTestPersonResponseDTO() {
        return getTestPersonResponseDTOs().get(0);
    }

    public static List<PersonResponseDTO> getTestPersonResponseDTOs() {
        List<PersonResponseDTO> personList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            PersonResponseDTO person = new PersonResponseDTO();
            person.setId(i);
            person.setEmail("person" + i + "@foo.bar");
            person.setEventIds(new ArrayList<>());
            person.getEventIds().add(i);
            personList.add(person);
        }
        return personList;
    }

    public static PersonRequestDTO getTestPersonRequestDTO() {
        PersonRequestDTO personRequestDTO = new PersonRequestDTO();
        personRequestDTO.setId(1);
        personRequestDTO.setEmail("person1@foo.bar");
        personRequestDTO.setPassword("password1");
        personRequestDTO.setEventIds(new ArrayList<>());
        personRequestDTO.getEventIds().add(1);

        return personRequestDTO;
    }

    public static EventDTO getTestEventDTO() {
        return getTestEventDTOs().get(0);
    }

    public static List<EventDTO> getTestEventDTOs() {
        List<EventDTO> eventDTOs = new ArrayList<>();
        List<PersonResponseDTO> testPersonResponseDTOS = getTestPersonResponseDTOs();

        for (int i = 1; i <= 4; i++) {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setId(i);
            eventDTO.setOwnerId(testPersonResponseDTOS.get(i - 1).getId());
            eventDTO.setName("Event" + i);
            eventDTO.setDescription("Description" + i);
            eventDTO.setDate(Date.valueOf("2022-06-04"));

            List<Integer> ticketSet = new ArrayList<>();
            ticketSet.add(i);

            eventDTO.setTicketIds(ticketSet);

            eventDTOs.add(eventDTO);
        }

        return eventDTOs;
    }

    public static TicketDTO getTestTicketDTO() {
        return getTestTicketDTOs().get(0);
    }

    public static TicketDTO getInvalidTestTicketDTO() {
        TicketDTO dto = new TicketDTO();
        dto.setId(0);
        return dto;
    }

    public static List<TicketDTO> getTestTicketDTOs() {
        List<TicketDTO> tickets = new ArrayList<>();

        int eventId = 1;

        for (int i = 1; i <= 4; i++) {
            TicketDTO ticket = new TicketDTO();
            ticket.setId(i);
            ticket.setName("Ticket" + i);
            ticket.setDescription("Description" + i);
            ticket.setEventId(eventId);
            ticket.setAmountToBuy(i);
            tickets.add(ticket);
        }

        return tickets;
    }
}
