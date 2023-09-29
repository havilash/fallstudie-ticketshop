package ch.bbcag.backend.ticketshop.data.dto;

import ch.bbcag.backend.ticketshop.person.PersonResponseDTO;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(23)
public class PersonResponseDTOTest {
    @Test
    @Order(1)
    public void constructor_hasEmptyConstructor() {
        Class<?> personResponseDTOClass = PersonResponseDTO.class;
        boolean doesPersonResponseDTOClassHaveEmptyConstructor = Arrays.stream(personResponseDTOClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesPersonResponseDTOClassHaveEmptyConstructor);
    }

    @Test
    @Order(2)
    public void idField_doesExist() {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredField("id"));
    }

    @Test
    @Order(3)
    public void emailField_doesExist() {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredField("email"));
    }

    @Test
    @Order(4)
    public void eventIdsField_doesExist() {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredField("eventIds"));
    }

    @Test
    @Order(5)
    public void passwordField_doesNotExist() {
        assertThrows(NoSuchFieldException.class, () -> PersonResponseDTO.class.getDeclaredField("password"));
    }

    @Test
    @Order(6)
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(7)
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredMethod("hashCode"));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setEmail, java.lang.String", "setEventIds, java.util.List"})
    @Order(8)
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getEmail", "getEventIds"})
    @Order(8)
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredMethod(getterName));
    }
}
