package ch.bbcag.backend.ticketshop.person;

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
import javax.validation.Valid;

@RestController
@RequestMapping(PersonController.PATH)
public class PersonController {
    public static final String PATH = "/persons";

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found",
                    content = @Content(schema = @Schema(implementation = PersonResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Person not found",
                    content = @Content)
    })
    public ResponseEntity<?> findById(@Parameter(description = "Id of person to get") @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(personService.findById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
    }

    @PutMapping("{id}/role/{roleName}")
    @Operation(summary = "Add a role to person, you need to be admin to do that")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event was edited successfully",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed to update that event",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "409", description = "Event could not be edited",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> update(@Parameter(description = "Id of person to modify") @PathVariable Integer id, @Parameter(description = "Role of person to add") @PathVariable String roleName) {
        try {
            return ResponseEntity.ok(personService.assignRole(roleName, id));
        } catch (DataIntegrityViolationException | EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}/role/{roleName}")
    @Operation(summary = "Delete a role to person, you need to be event manager to do that")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Event was deleted",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "403", description = "You are not allowed to delete that event",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(schema = @Schema(implementation = EventDTO.class))),
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> delete(@Parameter(description = "Id of person to modify") @PathVariable Integer id, @Parameter(description = "Role of person to delete") @PathVariable String roleName) {
        try {
            personService.removeRole(roleName, id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Role of person could not be deleted");
        }
    }
}
