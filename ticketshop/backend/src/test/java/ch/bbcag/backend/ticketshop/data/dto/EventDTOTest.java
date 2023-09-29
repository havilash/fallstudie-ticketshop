package ch.bbcag.backend.ticketshop.data.dto;

import ch.bbcag.backend.ticketshop.event.EventDTO;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(25)
public class EventDTOTest {

    @Test
    @Order(1)
    public void constructor_hasEmptyConstructor() {
        Class<?> eventDTOClass = EventDTO.class;
        boolean doesEventDTOClassHaveEmptyConstructor = Arrays.stream(eventDTOClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
        assertTrue(doesEventDTOClassHaveEmptyConstructor);
    }

    @Test
    @Order(2)
    public void idField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("id"));
    }

    @Test
    @Order(3)
    public void nameField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("name"));
    }

    @Test
    @Order(11)
    public void nameField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(EventDTO.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(4)
    public void descriptionField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("description"));
    }

    @Test
    @Order(5)
    public void ownerIdField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("ownerId"));
    }

    @Test
    @Order(12)
    public void ownerIdField_isAnnotatedWithNotNull() {
        try {
            assertNotNull(EventDTO.class
                    .getDeclaredField("ownerId")
                    .getDeclaredAnnotation(NotNull.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(6)
    public void dateField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("date"));
    }

    @Test
    @Order(13)
    public void dateField_isAnnotatedWithNotNull() {
        try {
            assertNotNull(EventDTO.class
                    .getDeclaredField("date")
                    .getDeclaredAnnotation(NotNull.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(7)
    public void ticketIdsField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("ticketIds"));
    }

    @Test
    @Order(8)
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(9)
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredMethod("hashCode"));
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getName", "getOwnerId", "getTicketIds", "getDate", "getDescription"})
    @Order(10)
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setName, java.lang.String", "setOwnerId, java.lang.Integer", "setTicketIds, java.util.List", "setDate, java.sql.Date", "setDescription, java.lang.String"})
    @Order(10)
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }
}
