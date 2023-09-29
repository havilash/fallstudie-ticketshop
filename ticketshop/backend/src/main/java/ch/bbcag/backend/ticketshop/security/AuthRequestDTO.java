package ch.bbcag.backend.ticketshop.security;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class AuthRequestDTO {
    @NotBlank(message = "email must not be empty")
    private String email;

    @NotBlank(message = "password must not be empty")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthRequestDTO that)) {
            return false;
        }
        return Objects.equals(email, that.email)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
