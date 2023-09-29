package ch.bbcag.backend.ticketshop.ticket;

import ch.bbcag.backend.ticketshop.FailedValidationException;
import ch.bbcag.backend.ticketshop.event.Event;
import ch.bbcag.backend.ticketshop.event.EventDTO;
import ch.bbcag.backend.ticketshop.event.EventMapper;
import ch.bbcag.backend.ticketshop.ticket.Ticket;
import ch.bbcag.backend.ticketshop.ticket.TicketDTO;
import ch.bbcag.backend.ticketshop.ticket.TicketMapper;
import ch.bbcag.backend.ticketshop.ticket.TicketRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketDTO create(TicketDTO ticketDTO) {
        Ticket ticket = TicketMapper.fromDTO(ticketDTO);
        ticketRepository.save(ticket);
        return ticketDTO;
    }

    public List<TicketDTO> findAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(TicketMapper::toDTO).toList();
    }

    public List<TicketDTO> findAllUnsold() {
        List<Ticket> tickets = ticketRepository.findAllUnsold();
        return tickets.stream().map(TicketMapper::toDTO).toList();

    }

    public TicketDTO findById(Integer id) {
        return TicketMapper.toDTO(ticketRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TicketDTO update(TicketDTO ticketDTO, Integer id) {
        Ticket existingTicket = ticketRepository.findByIdForUpdate(id);
        if (existingTicket == null) { throw new EntityNotFoundException(); }
        Ticket changingTicket = TicketMapper.fromDTO(ticketDTO);
        if (existingTicket.getAmount() > changingTicket.getAmount()) { throw new DataIntegrityViolationException("Invalid amount"); }
        mergeTicket(existingTicket, changingTicket);
        return TicketMapper.toDTO(ticketRepository.save(existingTicket));
    }



    public List<TicketDTO> buy(List<TicketDTO> ticketDTOs) {
        List<Integer> buyTicketIds = ticketDTOs
                .stream()
                .map(TicketDTO::getId)
                .toList();

        List<Ticket> buyTickets = ticketDTOs
                .stream()
                .map(TicketMapper::fromDTO)
                .toList();

        List<Ticket> dbTickets = (List<Ticket>) ticketRepository.findAllByIdForUpdate(buyTicketIds);

        if (dbTickets.size() != buyTickets.size())  { throw new EntityNotFoundException(); }

        mergeTickets(dbTickets, buyTickets);

        ticketRepository.saveAll(dbTickets);

        return dbTickets
                .stream()
                .map(TicketMapper::toDTO)
                .toList();
    }

    private void mergeTicket(Ticket existing, Ticket changing) {
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
        if (changing.getAmount() != null) {
            existing.setAmount(changing.getAmount());
        }

        if (changing.getEvent() != null) {
            existing.setEvent(changing.getEvent());
        }

        if (!errors.isEmpty()) { throw new FailedValidationException(errors); }
    }


    private void mergeTickets(List<Ticket> existingTickets, List<Ticket> changingTickets) {
        for (Ticket changingTicket : changingTickets) {
            Ticket existingTicket = existingTickets
                    .stream()
                    .filter(obj -> Objects.equals(obj.getId(), changingTicket.getId()))
                    .findFirst()
                    .orElseThrow(EntityNotFoundException::new);

            if (existingTicket.getAmount() < changingTicket.getAmount()) {throw new DataIntegrityViolationException("Invalid amount");}

            changingTicket.setAmount(existingTicket.getAmount() - changingTicket.getAmount());

            mergeTicket(existingTicket, changingTicket);
        }
    }

    public void deleteById(Integer id)  {
        ticketRepository.deleteById(id);
    }
}
