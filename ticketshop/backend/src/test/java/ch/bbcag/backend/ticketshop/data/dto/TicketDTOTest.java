package ch.bbcag.backend.ticketshop.data.dto;

import ch.bbcag.backend.ticketshop.ticket.TicketDTO;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(26)
public class TicketDTOTest {
    @Test
    @Order(1)
    public void constructor_hasEmptyConstructor() {
        Class<?> ticketDTOClass = TicketDTO.class;
        boolean doesEventClassHaveEmptyConstructor = Arrays.stream(ticketDTOClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesEventClassHaveEmptyConstructor);
    }

    @Test
    @Order(2)
    public void idField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("id"));
    }

    @Test
    @Order(3)
    public void nameField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("name"));
    }

    @Test
    @Order(4)
    public void descriptionField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("description"));
    }

    @Test
    @Order(5)
    public void amountToBuyField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("amountToBuy"));
    }

    @Test
    @Order(6)
    public void eventIdField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("eventId"));
    }

    @Test
    @Order(10)
    public void eventIdField_isAnnotatedWithNotNull() {
        try {
            assertNotNull(TicketDTO.class
                    .getDeclaredField("eventId")
                    .getDeclaredAnnotation(NotNull.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(7)
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(8)
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredMethod("hashCode"));
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getName", "getDescription", "getAmountToBuy", "getEventId"})
    @Order(9)
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setName, java.lang.String", "setDescription, java.lang.String", "setAmountToBuy, java.lang.Integer", "setEventId, java.lang.Integer"})
    @Order(9)
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }
}
