package ch.bbcag.backend.ticketshop.person;

import java.util.List;
import java.util.Objects;

public class PersonResponseDTO {
    private Integer id;

    private String email;

    private List<Integer> eventIds;

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

    public List<Integer> getEventIds() {
        return eventIds;
    }

    public void setEventIds(List<Integer> eventIds) {
        this.eventIds = eventIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonResponseDTO that = (PersonResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(eventIds, that.eventIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, eventIds);
    }
}
