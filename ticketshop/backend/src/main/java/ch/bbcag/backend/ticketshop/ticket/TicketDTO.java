package ch.bbcag.backend.ticketshop.ticket;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TicketDTO {
    private Integer id;

    private String name;

    private String description;

    private Integer amountToBuy;

    @NotNull
    private Integer eventId;

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

    public Integer getAmountToBuy() {
        return amountToBuy;
    }

    public void setAmountToBuy(Integer amountToBuy) {
        this.amountToBuy = amountToBuy;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO ticketDTO = (TicketDTO) o;
        return Objects.equals(id, ticketDTO.id) && Objects.equals(name, ticketDTO.name) && Objects.equals(description, ticketDTO.description) && Objects.equals(amountToBuy, ticketDTO.amountToBuy) && Objects.equals(eventId, ticketDTO.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, amountToBuy, eventId);
    }
}
