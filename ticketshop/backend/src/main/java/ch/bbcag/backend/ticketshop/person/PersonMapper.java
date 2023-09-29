package ch.bbcag.backend.ticketshop.person;


import ch.bbcag.backend.ticketshop.event.Event;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonMapper {
    public static PersonResponseDTO toDTO(Person person) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();

        personResponseDTO.setId(person.getId());
        personResponseDTO.setEmail(person.getEmail());
        if (person.getEvents() != null) {
            List<Integer> eventIds = person
                    .getEvents()
                    .stream()
                    .map(Event::getId)
                    .toList();

            personResponseDTO.setEventIds(eventIds);
        }

        return personResponseDTO;
    }

    public static Person fromDTO(PersonRequestDTO personRequestDTO) {
        Person person = new Person();

        person.setId(personRequestDTO.getId());
        person.setEmail(personRequestDTO.getEmail());
        if (personRequestDTO.getEventIds() != null) {
            Set<Event> events = personRequestDTO
                    .getEventIds()
                    .stream()
                    .map(Event::new)
                    .collect(Collectors.toSet());

            person.setEvents(events);
        }
        person.setPassword(personRequestDTO.getPassword());

        return person;
    }
}
