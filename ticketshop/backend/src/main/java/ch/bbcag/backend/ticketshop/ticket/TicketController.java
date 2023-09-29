package ch.bbcag.backend.ticketshop.ticket;

import ch.bbcag.backend.ticketshop.event.EventDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(TicketController.PATH)
public class TicketController {
    public static final String PATH = "/tickets";

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @Operation(summary = "Get all tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets found",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
    })
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @PostMapping
    @Operation(summary = "Create a ticket, you need to be event manager to do that")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket was created successfully",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed to create that ticket",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "409", description = "Ticket could not be created",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> create(@Parameter(description = "The ticket to create") @Valid @RequestBody TicketDTO ticket) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.create(ticket));
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Get one ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket found",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
    })
    public ResponseEntity<?> findById(@Parameter(description = "Id of ticket to get") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ticketService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update an ticket, you need to be ticket manager to do that")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket was edited successfully",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed to update that ticket",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
            @ApiResponse(responseCode = "409", description = "Ticket could not be edited",
                    content = @Content(schema = @Schema(implementation = TicketDTO.class))),
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> update(@Parameter(description = "The ticket to update") @Valid @RequestBody TicketDTO ticket, @Parameter(description = "Id of ticket to modify") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ticketService.update(ticket, id));
        } catch (DataIntegrityViolationException | EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("buy")
    @Operation(summary = "Buy tickets. Only amount can be changed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tickets were edited successfully",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "409", description = "Tickets could not be edited",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
    })
    public ResponseEntity<?> buy(@Parameter(description = "The tickets to buy") @Valid @RequestBody List<TicketDTO> tickets) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ticketService.buy(tickets));
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
