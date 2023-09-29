package ch.bbcag.backend.ticketshop.data.entity;

import ch.bbcag.backend.ticketshop.event.Event;
import ch.bbcag.backend.ticketshop.person.Person;
import ch.bbcag.backend.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(11)
class EventTest {

    @Test
    @Order(1)
    public void constructor_hasEmptyConstructor() {
        Class<?> eventClass = Event.class;
        boolean doesEventClassHaveEmptyConstructor = Arrays.stream(eventClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesEventClassHaveEmptyConstructor);
    }

    @Test
    @Order(11)
    public void class_isAnnotatedWithEntity() {
        assertNotNull(Event.class.getDeclaredAnnotation(Entity.class));
    }

    @Test
    @Order(2)
    public void idField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("id"));
    }

    @Test
    @Order(12)
    public void idField_isAnnotatedWithId() {
        try {
            assertNotNull(Event.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(Id.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(13)
    public void idField_isAnnotatedWithGeneratedValue() {
        try {
            assertNotNull(Event.class
                    .getDeclaredField("id")
                    .getDeclaredAnnotation(GeneratedValue.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(13)
    public void idFieldGeneratedValueAnnotation_isStrategyIdentity() {
        try {
            assertEquals(GenerationType.IDENTITY, Event.class
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
        assertDoesNotThrow(() -> Event.class.getDeclaredField("name"));
    }

    @Test
    @Order(4)
    public void ownerField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("owner"));
    }

    @Test
    @Order(14)
    public void ownerFieldCardinality_isManyToOne() {
        try {
            assertNotNull(Event.class
                    .getDeclaredField("owner")
                    .getDeclaredAnnotation(ManyToOne.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(15)
    public void ownerField_isAnnotatedWithJoinColumn() {
        try {
            assertNotNull(Event.class
                    .getDeclaredField("owner")
                    .getDeclaredAnnotation(JoinColumn.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(15)
    public void ownerFieldJoinColumnAnnotation_isNameOwnerId() {
        try {
            assertEquals("owner_id", Event.class
                    .getDeclaredField("owner")
                    .getDeclaredAnnotation(JoinColumn.class)
                    .name()
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(5)
    public void ticketsField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("tickets"));
    }

    @Test
    @Order(16)
    public void ticketsFieldCardinality_isOneToMany() {
        try {
            assertNotNull(Event.class
                    .getDeclaredField("tickets")
                    .getDeclaredAnnotation(OneToMany.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(16)
    public void ticketsFieldOneToManyAnnotation_isMappedByEvent() {
        try {
            assertEquals("event", Event.class
                    .getDeclaredField("tickets")
                    .getDeclaredAnnotation(OneToMany.class)
                    .mappedBy()
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(6)
    public void dateField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("date"));
    }

    @Test
    @Order(7)
    public void descriptionField_doesExist() {
        assertDoesNotThrow(() -> Event.class.getDeclaredField("description"));
    }

    @Test
    @Order(8)
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> Event.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(8)
    public void equalsMethod_comparesOnlyId() {
        Event event1 = DataUtil.getTestEvent();
        Event event2 = DataUtil.getTestEvent();

        event1.setName("NotSameName");
        event1.setDescription("NotSameDescription");
        event1.setDate(Date.valueOf("1970-01-01"));

        assertEquals(event1, event2);
    }

    @Test
    @Order(9)
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> Event.class.getDeclaredMethod("hashCode"));
    }

    @Test
    @Order(9)
    public void hashCodeMethod_hashesId() {
        Event event1 = DataUtil.getTestEvent();
        Event event2 = DataUtil.getTestEvent();

        event1.setDescription("Different description");
        event1.setName("Not same name");
        event1.setOwner(new Person());

        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getName", "getOwner", "getDate", "getDescription", "getTickets"})
    @Order(10)
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> Event.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setName, java.lang.String", "setOwner, ch.bbcag.backend.ticketshop.person.Person", "setDate, java.sql.Date", "setDescription, java.lang.String", "setTickets, java.util.Set"})
    @Order(10)
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> Event.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }
}
