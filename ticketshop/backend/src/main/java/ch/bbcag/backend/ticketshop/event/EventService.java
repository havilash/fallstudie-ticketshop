package ch.bbcag.backend.ticketshop.event;

import ch.bbcag.backend.ticketshop.FailedValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDTO create(EventDTO eventDTO) {
        Event event = EventMapper.fromDTO(eventDTO);
        eventRepository.save(event);
        return eventDTO;
    }

    public EventDTO update(EventDTO eventDTO, Integer eventId) {
        Event existingEvent = eventRepository.findById(eventId).orElseThrow(EntityNotFoundException::new);

        mergeEvent(existingEvent, EventMapper.fromDTO(eventDTO));

        return EventMapper.toDTO(eventRepository.save(existingEvent));
    }

    public List<EventDTO> findAll() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(EventMapper::toDTO).toList();

    }

    public EventDTO findById(Integer id) {
        return EventMapper.toDTO(eventRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }


    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }

    private void mergeEvent(Event existing, Event changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getName() != null) {
            if (StringUtils.isNotBlank(changing.getName())) {
                existing.setName(changing.getName());
            } else {
                errors.put("name", List.of("name must not be empty"));
            }
        }
        if (changing.getDescription() != null) {
            if (changing.getDescription().length() >= 0 && changing.getDescription().length() <= 255 ) {
                existing.setDescription(changing.getDescription());
            } else {
                errors.put("description", List.of("Description must be between 0 and 255"));
            }
        }
        if (changing.getDate() != null) {
            existing.setDate(changing.getDate());
        }

        if (changing.getAmount() != null) {
            existing.setAmount(changing.getAmount());
        }

        if (changing.getOwner() != null) {
            existing.setOwner(changing.getOwner());
        }

        if (changing.getTickets() != null) {
            existing.setTickets(changing.getTickets());
        }

        if (!errors.isEmpty()) { throw new FailedValidationException(errors); }

    }
}
