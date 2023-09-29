package ch.bbcag.backend.ticketshop.person;

import java.util.List;
import java.util.Objects;

public class PersonRequestDTO extends PersonResponseDTO{
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonRequestDTO that = (PersonRequestDTO) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), password);
    }
}
