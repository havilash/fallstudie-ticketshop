package ch.bbcag.backend.ticketshop.data.entity;

import ch.bbcag.backend.ticketshop.person.Person;
import ch.bbcag.backend.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(10)
class PersonTest {

    @Test
    @Order(1)
    public void constructor_hasEmptyConstructor() {
        Class<?> personClass = Person.class;
        boolean doesPersonClassHaveEmptyConstructor = Arrays.stream(personClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesPersonClassHaveEmptyConstructor);
    }

    @Test
    @Order(9)
    public void class_isAnnotatedWithEntity() {
        assertNotNull(Person.class.getDeclaredAnnotation(Entity.class));
    }

    @Test
    @Order(2)
    public void idField_doesExist() {
        assertDoesNotThrow(() -> Person.class.getDeclaredField("id"));
    }

    @Test
    @Order(10)
    public void idField_isAnnotatedWithId() {
        try {
            assertNotNull(Person.class
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
            assertNotNull(Person.class
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
            assertEquals(GenerationType.IDENTITY, Person.class
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
    public void emailField_doesExist() {
        assertDoesNotThrow(() -> Person.class.getDeclaredField("email"));
    }

    @Test
    @Order(12)
    public void emailField_isAnnotatedWithColumn() {
        try {
            assertNotNull(Person.class
                    .getDeclaredField("email")
                    .getDeclaredAnnotation(Column.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(12)
    public void emailFieldColumn_isUnique() {
        try {
            assertTrue(Person.class
                    .getDeclaredField("email")
                    .getDeclaredAnnotation(Column.class)
                    .unique());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(4)
    public void passwordHashField_doesExist() {
        assertDoesNotThrow(() -> Person.class.getDeclaredField("password"));
    }

    @Test
    @Order(5)
    public void eventsField_doesExist() {
        assertDoesNotThrow(() -> Person.class.getDeclaredField("events"));
    }

    @Test
    @Order(13)
    public void eventsFieldCardinality_isOneToMany() {
        try {
            assertNotNull(Person.class
                    .getDeclaredField("events")
                    .getDeclaredAnnotation(OneToMany.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(13)
    public void eventsFieldOneToManyAnnotation_isMappedByOwner() {
        try {
            assertEquals("owner", Person.class
                    .getDeclaredField("events")
                    .getDeclaredAnnotation(OneToMany.class)
                    .mappedBy()
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    @Order(6)
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> Person.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Order(6)
    public void equalsMethod_comparesOnlyId() {
        Person person1 = DataUtil.getTestPerson();
        Person person2 = DataUtil.getTestPerson();

        person1.setEmail("NotSameEmail@foo.bar");
        person1.setPassword("NotSamePassword");

        assertEquals(person1, person2);
    }

    @Test
    @Order(7)
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> Person.class.getDeclaredMethod("hashCode"));
    }

    @Test
    @Order(7)
    public void hashCodeMethod_hashesId() {
        Person person1 = DataUtil.getTestPerson();
        Person person2 = DataUtil.getTestPerson();

        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @ParameterizedTest
    @CsvSource(value = {"getId", "getEmail", "getPassword", "getEvents"})
    @Order(8)
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> Person.class.getDeclaredMethod(getterName));
    }

    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Integer", "setEmail, java.lang.String", "setPassword, java.lang.String", "setEvents, java.util.Set"})
    @Order(8)
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> Person.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

}
