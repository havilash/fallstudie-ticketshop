package ch.bbcag.backend.ticketshop.data.repository;

import ch.bbcag.backend.ticketshop.person.Person;
import ch.bbcag.backend.ticketshop.person.PersonRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(20)
public class PersonRepositoryTest {

    @Test
    @Order(1)
    public void repository_isInterface() {
        assertTrue(PersonRepository.class.isInterface());
    }

    @Test
    @Order(2)
    public void repository_extendsJPARepository() {
        assertTrue(Arrays.asList(PersonRepository.class.getInterfaces()).contains(JpaRepository.class));
    }

    @Test
    @Order(3)
    public void repositoryEntity_isPerson() {
        assertTrue(Arrays.stream(PersonRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[0].equals(Person.class)));
    }

    @Test
    @Order(3)
    public void repositoryId_isInteger() {
        assertTrue(Arrays.stream(PersonRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[1].equals(Integer.class)));
    }

    @Test
    @Order(4)
    public void repositoryFindPersonByEmail_exists() {
        assertDoesNotThrow(() -> PersonRepository.class.getDeclaredMethod("findPersonByEmail", String.class));
    }

    @Test
    @Order(4)
    public void repositoryFindPersonByEmail_returnsPerson() {
        try {
            assertEquals(Person.class, PersonRepository.class.getDeclaredMethod("findPersonByEmail", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

}
