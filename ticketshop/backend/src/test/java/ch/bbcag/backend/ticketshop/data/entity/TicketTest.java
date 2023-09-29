package ch.bbcag.backend.ticketshop.data.entity;

import ch.bbcag.backend.ticketshop.ticket.Ticket;
import ch.bbcag.backend.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(12)
class TicketTest {

    @Test
    @Order(1)
    public void constructor_hasEmptyConstructor() {
        Class<?> personClass = Ticket.class;
        boolean doesPersonClassHaveEmptyConstructor = Arrays.stream(personClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesPersonClassHaveEmptyConstructor);
    }

    @Test
    @Order(9)
    public void class_isAnnotatedWithEntity() {
        assertNotNull(Ticket.class.getDeclaredAnnotation(Entity.class));
    }

    @Test
    @Order(2)
    public void idField_doesExist() {
        assertDoesNotThrow(() -> Ticket.class.getDeclaredField("id"));
    }

    @Test
    @Order(10)
    public void idField_isAnnotatedWithId() {
        try {
            assertNotNull(Ticket.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(Id.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(11)
    public void idField_isAnnotatedWithGeneratedValue() {
        try {
            assertNotNull(Ticket.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(GeneratedValue.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(11)
    public void idFieldGeneratedValueAnnotation_isStrategyIdentity() {
        try {
            assertEquals(GenerationType.IDENTITY, Ticket.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(GeneratedValue.class)
                    .strategy()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(3)
    public void nameField_doesExist() {
        assertDoesNotThrow(() -> Ticket.class.getDeclaredField("name"));
    }

    @Test
    @Order(4)
    public void eventField_doesExist() {
        assertDoesNotThrow(() -> Ticket.class.getDeclaredField("event"));
    }

    @Test
    @Order(12)
    public void eventFieldCardinality_isManyToOne() {
        try {
            assertNotNull(Ticket.class
                    .getDeclaredField("event")
                    .getDeclaredAnnotation(ManyToOne.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(13)
    public void eventField_isAnnotatedWithJoinColumn() {
        try {
            assertNotNull(Ticket.class
                    .getDeclaredField("event")
                    .getDeclaredAnnotation(JoinColumn.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(5)
    public void amountField_doesExist() {
        assertDoesNotThrow(() -> Ticket.class.getDeclaredField("amount"));
    }

    @Test
    @Order(6)
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> Ticket.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(6)
    public void equalsMethod_doesCompareOnlyId() {
        Ticket ticket1 = DataUtil.getTestTicket();
        Ticket ticket2 = DataUtil.getTestTicket();

        ticket1.setName("NotSameName");
        ticket1.setDescription("NotSameDescription");
        ticket1.setAmount(1234);

        assertEquals(ticket1, ticket2);
    }

    @Test
    @Order(7)
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> Ticket.class.getDeclaredMethod("hashCode"));
    }

    @Test
    @Order(7)
    public void hashCodeMethod_hashesOnlyId() {
        Ticket ticket1 = DataUtil.getTestTicket();
        Ticket ticket2 = DataUtil.getTestTicket();

        ticket1.setName("Not same name");

        assertEquals(ticket1.hashCode(), ticket2.hashCode());
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getName", "getDescription", "getAmount", "getEvent"})
    @Order(8)
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> Ticket.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setName, java.lang.String", "setDescription, java.lang.String", "setAmount, java.lang.Integer", "setEvent, ch.bbcag.backend.ticketshop.event.Event"})
    @Order(8)
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> Ticket.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }
}
