package ch.bbcag.backend.ticketshop.security;

import java.util.Objects;

public class AuthResponseDTO {
    private Integer id;
    private String email;

    public AuthResponseDTO(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthResponseDTO that)) {
            return false;
        }
        return Objects.equals(id, that.id)
                && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
