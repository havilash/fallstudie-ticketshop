package ch.bbcag.backend.ticketshop.event;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class EventDTO {
    private Integer id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Date date;

    private List<Integer> ticketIds;

    @NotNull
    private Integer ownerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Integer> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Integer> ticketIds) {
        this.ticketIds = ticketIds;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDTO eventDTO = (EventDTO) o;
        return Objects.equals(id, eventDTO.id) && Objects.equals(name, eventDTO.name) && Objects.equals(description, eventDTO.description) && Objects.equals(date, eventDTO.date) && Objects.equals(ticketIds, eventDTO.ticketIds) && Objects.equals(ownerId, eventDTO.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, date, ticketIds, ownerId);
    }
}
