package ch.bbcag.backend.ticketshop.role;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityRole implements GrantedAuthority {

    private final String authority;

    public AuthorityRole(Role role) {
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
