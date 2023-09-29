package ch.bbcag.backend.ticketshop.data.security;

//import ch.bbcag.backend.ticketshop.role.RoleService;
import ch.bbcag.backend.ticketshop.security.BbcUserDetailsService;
import ch.bbcag.backend.ticketshop.security.SecurityConfiguration;
import ch.bbcag.backend.ticketshop.ticket.TicketController;
import ch.bbcag.backend.ticketshop.ticket.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TicketController.class)
@AutoConfigureMockMvc
@Import(SecurityConfiguration.class)
@WithMockUser(authorities = "NOT_THE_NEEDED_ONES")
public class TicketControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BbcUserDetailsService userDetailsService;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private TicketService ticketService;
//
//    @MockBean
//    private RoleService roleService;

    @Test
    public void checkPatch_whenNotAuthorized_thenIsForbidden() throws Exception {
        mockMvc.perform(patch(TicketController.PATH + "/" + 1)
                        .contentType("application/json")
                        .content("{\"eventId\":1,\"amountToBuy\":123}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void checkPost_whenNotAuthorized_thenIsForbidden() throws Exception {
        mockMvc.perform(post(TicketController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Ticket1\",\"eventId\":1,\"description\":\"Description1\",\"amountToBuy\":10}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ADMIN")
    public void checkPatch_whenAuthorized_thenIsOk() throws Exception {
        mockMvc.perform(patch(TicketController.PATH + "/" + 1)
                        .contentType("application/json")
                        .content("{\"eventId\":1,\"amountToBuy\":123}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ADMIN")
    public void checkPost_whenAuthorized_thenIsOk() throws Exception {
        mockMvc.perform(post(TicketController.PATH)
                        .contentType("application/json")
                        .content("{\"name\":\"Ticket1\",\"eventId\":1,\"description\":\"Description1\",\"amountToBuy\":10}"))
                .andExpect(status().isCreated());
    }


}
