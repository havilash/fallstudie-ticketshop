package ch.bbcag.backend.ticketshop.data.repository;

import ch.bbcag.backend.ticketshop.event.Event;
import ch.bbcag.backend.ticketshop.event.EventRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Order(21)
public class EventRepositoryTest {

    @Test
    @Order(1)
    public void repository_isInterface() {
        assertTrue(EventRepository.class.isInterface());
    }

    @Test
    @Order(2)
    public void repository_extendsJPARepository() {
        assertTrue(Arrays.asList(EventRepository.class.getInterfaces()).contains(JpaRepository.class));
    }

    @Test
    @Order(3)
    public void repositoryEntity_isEvent() {
        assertTrue(Arrays.stream(EventRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[0].equals(Event.class)));
    }

    @Test
    @Order(3)
    public void repositoryId_isInteger() {
        assertTrue(Arrays.stream(EventRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[1].equals(Integer.class)));
    }

}
