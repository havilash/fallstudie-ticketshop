package ch.bbcag.backend.ticketshop.data.security;

import ch.bbcag.backend.ticketshop.event.EventController;
import ch.bbcag.backend.ticketshop.event.EventService;
//import ch.bbcag.backend.ticketshop.role.RoleService;
import ch.bbcag.backend.ticketshop.security.BbcUserDetailsService;
import ch.bbcag.backend.ticketshop.security.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventController.class)
@AutoConfigureMockMvc
@Import(SecurityConfiguration.class)
public class EventControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BbcUserDetailsService userDetailsService;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

//    @MockBean
//    private RoleService roleService;

    @MockBean
    private EventService eventService;

    /**
     * Smoke-Test to not get a 401/403
     *
     * @throws Exception
     */
    @Test
    public void checkFindAll_whenNotLoggedIn_thenIsOk() throws Exception {
        Mockito.when(eventService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(EventController.PATH))
                .andExpect(status().isOk());
    }

    /**
     * Smoke-Test to not get a 401/403
     *
     * @throws Exception
     */
    @Test
    public void checkGetInvalid_whenNotLoggedIn_thenNotFound() throws Exception {
        Mockito.when(eventService.findById(anyInt())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get(EventController.PATH + "/" + 0))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "NOT_THE_NEEDED_ONES")
    public void checkPatch_whenNotAuthorized_thenIsForbidden() throws Exception {
        mockMvc.perform(patch(EventController.PATH + "/" + 1)
                        .contentType("application/json")
                        .content("{\"name\":\"NewEventName\",\"ownerId\":1,\"date\":\"2022-06-04\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ADMIN")
    public void checkPatch_whenAuthorized_thenIsOK() throws Exception {
        mockMvc.perform(patch(EventController.PATH + "/" + 1)
                        .contentType("application/json")
                        .content("{\"name\":\"NewEventName\",\"ownerId\":1,\"date\":\"2022-06-04\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "NOT_THE_NEEDED_ONES")
    public void checkPost_whenNotAuthorized_thenIsForbidden() throws Exception {
        mockMvc.perform(post(EventController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Event1\",\"description\":\"Description1\",\"date\":\"2022-06-04\",\"ownerId\":1}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "NOT_THE_NEEDED_ONES")
    public void checkDelete_whenNotAuthorized_thenIsForbidden() throws Exception {
        mockMvc.perform(delete(EventController.PATH + "/" + 1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ADMIN")
    public void checkDelete_whenAuthorized_thenIsNoContent() throws Exception {
        mockMvc.perform(delete(EventController.PATH + "/" + 1))
                .andExpect(status().isNoContent());
    }
}
