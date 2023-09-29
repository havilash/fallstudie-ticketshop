package ch.bbcag.backend.ticketshop.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.w3c.dom.events.Event;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping(EventController.PATH)
public class EventController {
    public static final String PATH = "/events";

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    @Operation(summary = "Get all events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events found",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
    })
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @PostMapping
    @Operation(summary = "Create an event, you need to be event manager to do that")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event was created successfully",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed to create an event",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "409", description = "Event could not be created",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> create(@Parameter(description = "The event to create") @Valid @RequestBody EventDTO event) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(event));
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a single event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event found",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
    })
    public ResponseEntity<?> findById(@Parameter(description = "Id of event to get") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(eventService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete an event, you need to be event manager to do that")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event was deleted",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed to delete that event",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                content = @Content(schema = @Schema(implementation = EventDTO.class))),
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> delete(@Parameter(description = "Id of event to delete") @PathVariable Integer id) {
        try {
            eventService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event could not be deleted");
        }
    }

    @PatchMapping("{id}")
    @Operation(summary = "Update an event, you need to be event manager to do that")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event was edited successfully",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed to update that event",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "409", description = "Event could not be edited",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> update(@Parameter(description = "The event to update") @Valid @RequestBody EventDTO event, @Parameter(description = "Id of event to modify") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(eventService.update(event, id));
        } catch (DataIntegrityViolationException | EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
