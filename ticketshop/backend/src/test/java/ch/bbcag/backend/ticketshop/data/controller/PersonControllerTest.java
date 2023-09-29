package ch.bbcag.backend.ticketshop.data.controller;

import ch.bbcag.backend.ticketshop.person.PersonController;
import ch.bbcag.backend.ticketshop.person.PersonResponseDTO;
import ch.bbcag.backend.ticketshop.person.PersonService;
import ch.bbcag.backend.ticketshop.util.DataDTOUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Order(41)
@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkFindById_whenValidId_thenEventIsReturned() throws Exception {
        PersonResponseDTO expected = DataDTOUtil.getTestPersonResponseDTO();
        Mockito.when(personService.findById(eq(1))).thenReturn(expected);

        mockMvc.perform(get(PersonController.PATH + "/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("person1@foo.bar")));
    }

    @Test
    public void checkFindById_whenInvalidId_thenIsNotFound() throws Exception {
        Mockito.when(personService.findById(eq(0))).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get(PersonController.PATH + "/" + 0))
                .andExpect(status().isNotFound());
    }
}
