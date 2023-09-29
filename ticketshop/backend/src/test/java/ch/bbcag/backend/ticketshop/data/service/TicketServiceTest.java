package ch.bbcag.backend.ticketshop.data.service;

import ch.bbcag.backend.ticketshop.ticket.*;
import ch.bbcag.backend.ticketshop.ticket.Ticket;
import ch.bbcag.backend.ticketshop.util.DataDTOUtil;
import ch.bbcag.backend.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@Order(32)
@ExtendWith(SpringExtension.class)
public class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    @Order(4)
    public void checkCreateTicket_whenValidTicket_thenReturnTicketDTO() {
        TicketDTO expectedTicketDTO = DataDTOUtil.getTestTicketDTO();
        Ticket expectedTicket = DataUtil.getTestTicket();

        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(expectedTicket);

        TicketDTO actual = ticketService.create(expectedTicketDTO);

        assertEquals(expectedTicketDTO.getId(), actual.getId());
        assertEquals(expectedTicketDTO.getName(), actual.getName());
        assertEquals(expectedTicketDTO.getAmountToBuy(), actual.getAmountToBuy());
        assertEquals(expectedTicketDTO.getEventId(), actual.getEventId());
    }

    @Test
    @Order(4)
    public void checkCreateTicket_whenInvalidTicket_thenThrowConstraintViolation() {
        TicketDTO failingTicketDTO = DataDTOUtil.getTestTicketDTO();
        failingTicketDTO.setEventId(0);

        Mockito.when(ticketRepository.save(any(Ticket.class))).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> ticketService.create(failingTicketDTO));
    }

    @Test
    @Order(4)
    public void checkCreate_whenValidId_thenTicketDTOIsReturned() {
        TicketDTO expectedTicketDTO = DataDTOUtil.getTestTicketDTO();
        Ticket expectedTicket = DataUtil.getTestTicket();

        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(expectedTicket);

        TicketDTO actualTicketDTO = ticketService.create(expectedTicketDTO);

        assertEquals(expectedTicketDTO.getId(), actualTicketDTO.getId());
        assertEquals(expectedTicketDTO.getName(), actualTicketDTO.getName());
        assertEquals(expectedTicketDTO.getAmountToBuy(), actualTicketDTO.getAmountToBuy());
        assertEquals(expectedTicketDTO.getEventId(), actualTicketDTO.getEventId());
    }

    @Test
    @Order(3)
    public void checkFindById_whenValidId_thenTicketDTOIsReturned() {
        Ticket expected = DataUtil.getTestTicket();
        TicketDTO expectedTicketDTO = DataDTOUtil.getTestTicketDTO();

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(ticketRepository.findById(eq(1))).thenReturn(Optional.of(expected));

        TicketDTO actualTicketDTO = ticketService.findById(1);

        assertEquals(expectedTicketDTO.getId(), actualTicketDTO.getId());
        assertEquals(expectedTicketDTO.getName(), actualTicketDTO.getName());
        assertEquals(expectedTicketDTO.getAmountToBuy(), actualTicketDTO.getAmountToBuy());
        assertEquals(expectedTicketDTO.getEventId(), actualTicketDTO.getEventId());
    }

    @Test
    @Order(3)
    public void checkFindById_whenInvalidId_thenThrowsEntityNotFound() {

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We want eager loading here, use findById here"));

        Mockito.when(ticketRepository.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> ticketService.findById(0));
    }

    @Test
    @Order(1)
    public void checkFindAll_whenExistingTickets_thenTicketDTOListIsReturned() {
        List<TicketDTO> expectedTicketDTOs = DataDTOUtil.getTestTicketDTOs();

        List<Ticket> expectedTickets = DataUtil.getTestTickets();

        Mockito.when(ticketRepository.findAll()).thenReturn(expectedTickets);

        List<TicketDTO> actualTicketDTOs = ticketService.findAll();

        assertEquals(expectedTicketDTOs.size(), actualTicketDTOs.size());

        for (int i = 0; i < expectedTicketDTOs.size(); i++) {
            TicketDTO expectedTicketDTO = expectedTicketDTOs.get(i);
            TicketDTO actualTicketDTO = actualTicketDTOs.get(i);

            assertEquals(expectedTicketDTO.getId(), actualTicketDTO.getId());
            assertEquals(expectedTicketDTO.getName(), actualTicketDTO.getName());
            assertEquals(expectedTicketDTO.getAmountToBuy(), actualTicketDTO.getAmountToBuy());
            assertEquals(expectedTicketDTO.getEventId(), actualTicketDTO.getEventId());
        }
    }

    @Test
    @Order(5)
    public void checkUpdate_whenValidIdAndTicketDTO_thenReturnTicketDTO() {
        String newName = "NewTicketName";

        TicketDTO changingTicketDTO = DataDTOUtil.getTestTicketDTO();
        changingTicketDTO.setName(newName);

        Ticket unchangedTicket = DataUtil.getTestTicket();

        Ticket changedTicket = DataUtil.getTestTicket();
        changedTicket.setName(newName);

        TicketDTO expectedTicketDTO = DataDTOUtil.getTestTicketDTO();
        expectedTicketDTO.setName(newName);

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findByIdForUpdate(eq(1))).thenReturn(unchangedTicket);
        Mockito.when(ticketRepository.save(any(Ticket.class))).thenReturn(changedTicket);

        TicketDTO actual = ticketService.update(changingTicketDTO, 1);

        assertEquals(expectedTicketDTO.getName(), actual.getName());
        assertEquals(expectedTicketDTO.getAmountToBuy(), actual.getAmountToBuy());
    }

    @Test
    @Order(5)
    public void checkUpdate_whenInvalidAmount_thenDataIntegrityViolation() {
        TicketDTO changingTicketDTO = DataDTOUtil.getTestTicketDTO();
        changingTicketDTO.setAmountToBuy(-10);

        Ticket unchangedTicket = DataUtil.getTestTicket();

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findByIdForUpdate(eq(1))).thenReturn(unchangedTicket);

        assertThrows(DataIntegrityViolationException.class, () -> ticketService.update(changingTicketDTO, 1));
    }

    @Test
    @Order(5)
    public void checkUpdate_whenInvalidEventId_thenConstraintViolation() {
        TicketDTO failingTicketDTO = DataDTOUtil.getTestTicketDTO();
        failingTicketDTO.setEventId(0);

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findByIdForUpdate(failingTicketDTO.getId())).thenReturn(TicketMapper.fromDTO(failingTicketDTO));
        Mockito.when(ticketRepository.save(argThat(argument -> argument.getEvent().getId() == 0))).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> ticketService.update(failingTicketDTO, 1));
    }

    @Test
    @Order(5)
    public void checkUpdate_whenTicketNotExists_throwEntityNotFoundException() {
        Integer notExistingId = 0;
        TicketDTO changingTicket = DataDTOUtil.getTestTicketDTO();
        changingTicket.setId(notExistingId);

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findByIdForUpdate(eq(notExistingId))).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> ticketService.update(changingTicket, notExistingId));
    }

    @Test
    @Order(7)
    public void checkBuy_whenValidTicketDTOList_thenReturnTicketDTOList() {
        List<TicketDTO> changingTicketDTOs = DataDTOUtil.getTestTicketDTOs();

        List<TicketDTO> expectedTicketDTOs = DataDTOUtil.getTestTicketDTOs();
        expectedTicketDTOs.forEach(ticketDTO -> ticketDTO.setAmountToBuy(0));

        List<Ticket> unchangedTickets = DataUtil.getTestTickets();

        List<Ticket> changedTickets = DataUtil.getTestTickets();
        changedTickets.forEach(ticket -> {
            ticket.setAmount(0);
        });

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findAllByIdForUpdate(any())).thenReturn(unchangedTickets);
        Mockito.when(ticketRepository.saveAll(any())).thenReturn(changedTickets);

        List<TicketDTO> actualTicketDTOs = ticketService.buy(changingTicketDTOs);

        assertEquals(expectedTicketDTOs.size(), actualTicketDTOs.size());

        for (int i = 0; i < actualTicketDTOs.size(); i++) {
            TicketDTO expected = expectedTicketDTOs.get(i);
            TicketDTO actual = actualTicketDTOs.get(i);
            assertEquals(expected.getAmountToBuy(), actual.getAmountToBuy());
        }
    }

    @Test
    @Order(7)
    public void checkBuy_whenBuyingTooMuch_thenThrowDataIntegrityViolation() {
        List<TicketDTO> changingTicketDTOs = DataDTOUtil.getTestTicketDTOs();
        changingTicketDTOs.forEach(ticketDTO -> {
            ticketDTO.setAmountToBuy(100);
        });

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        List<Ticket> unchangedTickets = DataUtil.getTestTickets();
        Mockito.when(ticketRepository.findAllByIdForUpdate(any())).thenReturn(unchangedTickets);

        assertThrows(DataIntegrityViolationException.class, () -> ticketService.buy(changingTicketDTOs));
    }

    @Test
    @Order(7)
    public void checkBuy_whenNotExistingTickets_thenThrowEntityNotFoundException() {
        List<TicketDTO> changingTickets = DataDTOUtil.getTestTicketDTOs();
        List<Ticket> unchangedTickets = DataUtil.getTestTickets();
        unchangedTickets.remove(2);

        Mockito.when(ticketRepository.findAllByIdForUpdate(any())).thenReturn(unchangedTickets);

        assertThrows(EntityNotFoundException.class, () -> ticketService.buy(changingTickets));
    }

    @Test
    @Order(6)
    public void checkDeleteById_whenInvalidId_thenEmptyResultDataAccess() {
        Mockito.doThrow(EmptyResultDataAccessException.class).when(ticketRepository).deleteById(eq(0));

        assertThrows(EmptyResultDataAccessException.class, () -> ticketService.deleteById(0));
    }

    @Test
    @Order(2)
    public void checkFindAllUnsold_whenTicketsExist_thenReturnTicketDTOList() {
        List<TicketDTO> expectedTicketDTOs = DataDTOUtil.getTestTicketDTOs();

        List<Ticket> expectedTickets = DataUtil.getTestTickets();

        Mockito.when(ticketRepository.getById(any())).thenThrow(new AssertionError("getById is deprecated and should not be used"));
        Mockito.when(ticketRepository.findById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));
        Mockito.when(ticketRepository.getReferenceById(any())).thenThrow(new AssertionError("We need locking, use findByIdForUpdate here"));

        Mockito.when(ticketRepository.findAllUnsold()).thenReturn(expectedTickets);

        List<TicketDTO> actualTicketDTOs = ticketService.findAllUnsold();

        assertEquals(expectedTicketDTOs.size(), actualTicketDTOs.size());

        for (int i = 0; i < actualTicketDTOs.size(); i++) {
            TicketDTO expected = expectedTicketDTOs.get(i);
            TicketDTO actual = actualTicketDTOs.get(i);

            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getAmountToBuy(), actual.getAmountToBuy());
            assertEquals(expected.getEventId(), actual.getEventId());
        }
    }
}
