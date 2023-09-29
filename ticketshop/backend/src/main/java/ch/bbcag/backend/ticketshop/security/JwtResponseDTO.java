package ch.bbcag.backend.ticketshop.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

public class JwtResponseDTO extends AuthResponseDTO {
    private final Collection<? extends GrantedAuthority> authorities;
    private String accessToken;
    private String type = "Bearer";

    public JwtResponseDTO(String accessToken, Integer id, String email, Collection<? extends GrantedAuthority> authorities) {
        super(id, email);
        this.accessToken = accessToken;
        this.authorities = authorities;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JwtResponseDTO that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(accessToken, that.accessToken)
                && Objects.equals(type, that.type)
                && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accessToken, type, authorities);
    }
}
