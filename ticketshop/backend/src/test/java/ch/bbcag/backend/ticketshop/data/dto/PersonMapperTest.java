package ch.bbcag.backend.ticketshop.data.dto;

import ch.bbcag.backend.ticketshop.event.Event;
import ch.bbcag.backend.ticketshop.person.Person;
import ch.bbcag.backend.ticketshop.person.PersonMapper;
import ch.bbcag.backend.ticketshop.person.PersonRequestDTO;
import ch.bbcag.backend.ticketshop.person.PersonResponseDTO;
import ch.bbcag.backend.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static ch.bbcag.backend.ticketshop.util.DataDTOUtil.getTestPersonResponseDTO;
import static ch.bbcag.backend.ticketshop.util.DataDTOUtil.getTestPersonRequestDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(27)
public class PersonMapperTest {
    @Test
    @Order(1)
    public void checkFromDTO_whenValidPersonDTO_thenPersonIsReturned() {
        PersonRequestDTO testPersonRequestDTO = getTestPersonRequestDTO();

        Person expectedPerson = DataUtil.getTestPerson();

        Person actualPerson = PersonMapper.fromDTO(testPersonRequestDTO);

        assertEquals(expectedPerson.getId(), actualPerson.getId());
        assertEquals(expectedPerson.getEmail(), actualPerson.getEmail());
        assertEquals(expectedPerson.getPassword(), actualPerson.getPassword());
        assertEquals(expectedPerson.getEvents().size(), actualPerson.getEvents().size());

        for (int i = 0; i < expectedPerson.getEvents().size(); i++) {
            Event expectedEvent = expectedPerson.getEvents().stream().toList().get(i);
            Event actualEvent = actualPerson.getEvents().stream().toList().get(i);
            assertEquals(expectedEvent.getId(), actualEvent.getId());
        }

        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    @Order(2)
    public void checkToDTO_whenValidPerson_thenPersonDTOIsReturned() {
        Person testPerson = DataUtil.getTestPerson();

        PersonResponseDTO expectedPersonResponseDTO = getTestPersonResponseDTO();

        PersonResponseDTO actualPersonResponseDTO = PersonMapper.toDTO(testPerson);

        assertEquals(expectedPersonResponseDTO.getId(), actualPersonResponseDTO.getId());
        assertEquals(expectedPersonResponseDTO.getEmail(), actualPersonResponseDTO.getEmail());
        assertEquals(expectedPersonResponseDTO.getEventIds().size(), actualPersonResponseDTO.getEventIds().size());

        for (int i = 0; i < actualPersonResponseDTO.getEventIds().size(); i++) {
            int expectedEventId = expectedPersonResponseDTO.getEventIds().get(i);
            int actualEventId = actualPersonResponseDTO.getEventIds().get(i);
            assertEquals(expectedEventId, actualEventId);
        }

        assertEquals(expectedPersonResponseDTO, actualPersonResponseDTO);
    }
}
