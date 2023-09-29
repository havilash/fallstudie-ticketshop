package ch.bbcag.backend.ticketshop.data.security;

import ch.bbcag.backend.ticketshop.person.PersonController;
import ch.bbcag.backend.ticketshop.person.PersonService;
//import ch.bbcag.backend.ticketshop.role.RoleService;
import ch.bbcag.backend.ticketshop.role.RoleService;
import ch.bbcag.backend.ticketshop.security.BbcUserDetailsService;
import ch.bbcag.backend.ticketshop.security.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc
@Import(SecurityConfiguration.class)
@WithMockUser(authorities = "NOT_THE_NEEDED_ONES")
public class PersonControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BbcUserDetailsService userDetailsService;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    private PersonService personService;

    @MockBean
    private RoleService roleService;

    @Test
    public void checkPut_whenNotAuthorized_thenIsForbidden() throws Exception {
        mockMvc.perform(put(PersonController.PATH + "/1/role/ADMIN"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void checkDelete_whenNotAuthorized_thenIsForbidden() throws Exception {
        mockMvc.perform(delete(PersonController.PATH + "/1/role/ADMIN"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ADMIN")
    public void checkPut_whenAuthorized_thenIsOK() throws Exception {
        mockMvc.perform(put(PersonController.PATH + "/1/role/ADMIN"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ADMIN")
    public void checkDelete_whenAuthorized_thenIsNoContent() throws Exception {
        mockMvc.perform(delete(PersonController.PATH + "/1/role/ADMIN"))
                .andExpect(status().isNoContent());
    }
}
