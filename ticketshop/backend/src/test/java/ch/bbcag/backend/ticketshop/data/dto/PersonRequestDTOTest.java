package ch.bbcag.backend.ticketshop.data.dto;

import ch.bbcag.backend.ticketshop.person.PersonRequestDTO;
import ch.bbcag.backend.ticketshop.person.PersonResponseDTO;
import ch.bbcag.backend.ticketshop.util.DataDTOUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(24)
public class PersonRequestDTOTest {
    @Test
    @Order(1)
    public void constructor_hasEmptyConstructor() {
        Class<?> personRequestDTOClass = PersonRequestDTOTest.class;
        boolean doesPersonRequestDTOCLassHaveEmptyConstructor = Arrays.stream(personRequestDTOClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
        assertTrue(doesPersonRequestDTOCLassHaveEmptyConstructor);
    }

    @Test
    @Order(2)
    public void personRequestDTO_extendsPersonResponseDTO() {
        assertEquals(PersonRequestDTO.class.getSuperclass(), PersonResponseDTO.class);
    }

    @Test
    @Order(3)
    public void passwordField_doesExist() {
        assertDoesNotThrow(() -> PersonRequestDTO.class.getDeclaredField("password"));
    }

    @Test
    @Order(6)
    public void getPasswordMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonRequestDTO.class.getDeclaredMethod("getPassword"));
    }

    @Test
    @Order(6)
    public void setPasswordMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonRequestDTO.class.getDeclaredMethod("setPassword", String.class));
    }

    @Test
    @Order(4)
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonRequestDTO.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(5)
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> PersonRequestDTO.class.getDeclaredMethod("hashCode"));
    }

    @Test
    @Order(4)
    public void equalsMethod_usesSuperEquals() {
        PersonRequestDTO p1 = DataDTOUtil.getTestPersonRequestDTO();
        PersonRequestDTO p2 = DataDTOUtil.getTestPersonRequestDTO();

        p1.setEmail("NotSameEmail");

        assertNotEquals(p1, p2);
    }

    @Test
    @Order(5)
    public void hashCodeMethod_usesSuperHashCode() {
        PersonRequestDTO p1 = DataDTOUtil.getTestPersonRequestDTO();
        PersonRequestDTO p2 = DataDTOUtil.getTestPersonRequestDTO();

        p1.setEmail("NotSameEmail");

        assertNotEquals(p1.hashCode(), p2.hashCode());
    }
}
