package ch.bbcag.backend.ticketshop.data.controller;

import ch.bbcag.backend.ticketshop.event.EventDTO;
import ch.bbcag.backend.ticketshop.ticket.Ticket;
import ch.bbcag.backend.ticketshop.ticket.TicketController;
import ch.bbcag.backend.ticketshop.ticket.TicketDTO;
import ch.bbcag.backend.ticketshop.ticket.TicketService;
import ch.bbcag.backend.ticketshop.util.DataDTOUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Order(41)
@WebMvcTest(controllers = TicketController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    @Order(1)
    public void checkGet_whenNoParam_thenAllTicketsAreReturned() throws Exception {
        Mockito.when(ticketService.findAll()).thenReturn(DataDTOUtil.getTestTicketDTOs());

        mockMvc.perform(get(TicketController.PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(DataDTOUtil.JSON_ALL_TICKET_DTOS));
    }

    @Test
    @Order(2)
    public void checkFindById_whenValidId_thenTicketIsReturned() throws Exception {
        TicketDTO expected = DataDTOUtil.getTestTicketDTO();
        Mockito.when(ticketService.findById(eq(1))).thenReturn(expected);

        mockMvc.perform(get(TicketController.PATH + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Ticket1")));
    }

    @Test
    @Order(2)
    public void checkFindById_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.when(ticketService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get(TicketController.PATH + "/" + 0))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    public void checkPatch_whenValidTicket_thenIsOk() throws Exception {
        String newName = "NewTicketName";

        TicketDTO expected = DataDTOUtil.getTestTicketDTO();
        expected.setName(newName);

        Mockito.when(ticketService.update(any(TicketDTO.class), eq(1))).thenReturn(expected);

        mockMvc.perform(patch(TicketController.PATH + "/" + 1)
                        .contentType("application/json")
                        .content("{\"id\":1,\"name\":\"NewTicketName\",\"eventId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newName)));
    }

    @Test
    @Order(4)
    public void checkPatch_whenInvalidTicket_thenIsBadRequest() throws Exception {
        String newName = "";

        TicketDTO expected = DataDTOUtil.getTestTicketDTO();
        expected.setName(newName);

        mockMvc.perform(patch(TicketController.PATH + "/" + 1)
                        .contentType("application/json")
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(4)
    public void checkPatchBuy_whenValidTicket_thenIsOk() throws Exception {
        List<TicketDTO> expected = new ArrayList<>();
        expected.add(DataDTOUtil.getTestTicketDTO());

        Mockito.when(ticketService.buy(any())).thenReturn(expected);

        mockMvc.perform(patch(TicketController.PATH + "/buy")
                        .contentType("application/json")
                        .content("[{\"id\":1,\"name\":\"Ticket1\",\"description\":\"Description1\",\"eventId\":1,\"amountToBuy\":1}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Ticket1")));
    }

    @Test
    @Order(4)
    public void checkPatchBuy_whenInvalidTicket_thenIsBadRequest() throws Exception {
        List<TicketDTO> expected = new ArrayList<>();
        expected.add(DataDTOUtil.getTestTicketDTO());

        Mockito.when(ticketService.buy(any())).thenReturn(expected);

        mockMvc.perform(patch(TicketController.PATH + "/buy")
                        .contentType("application/json")
                        .content("[{\"id\":1,\"name\":\"Ticket1\",\"description\":\"Description1\",\"eventId\":1,\"amountToBuy\":1}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Ticket1")));
    }

    @Test
    @Order(3)
    public void checkPost_whenValidTicket_thenIsCreated() throws Exception {
        TicketDTO createdEvent = DataDTOUtil.getTestTicketDTO();

        Mockito.when(ticketService.create(any(TicketDTO.class))).thenReturn(createdEvent);

        mockMvc.perform(post(TicketController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Ticket1\",\"description\":\"Description1\",\"eventId\":1,\"amountToBuy\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    public void checkPost_whenInvalidTicket_thenIsBadRequest() throws Exception {
        Mockito.when(ticketService.create(any(TicketDTO.class))).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(post(TicketController.PATH)
                        .contentType("application/json")
                        .content("{\"description\":\"Description1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void checkPost_whenEventIdNotSet_thenIsBadRequest() throws Exception {
        mockMvc.perform(post(TicketController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Ticket1\",\"description\":\"Description1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void checkPost_whenInvalidEventId_thenIsConflict() throws Exception {
        Mockito.when(ticketService.create(any(TicketDTO.class))).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(post(TicketController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Ticket1\",\"description\":\"Description1\",\"eventId\":0}"))
                .andExpect(status().isConflict());
    }
}
