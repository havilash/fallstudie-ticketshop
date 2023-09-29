package ch.bbcag.backend.ticketshop.data.repository;

import ch.bbcag.backend.ticketshop.ticket.Ticket;
import ch.bbcag.backend.ticketshop.ticket.TicketRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Order(22)
public class TicketRepositoryTest {

    @Test
    @Order(1)
    public void repository_isInterface() {
        assertTrue(TicketRepository.class.isInterface());
    }

    @Test
    @Order(2)
    public void repository_extendsJPARepository() {
        assertTrue(Arrays.asList(TicketRepository.class.getInterfaces()).contains(JpaRepository.class));
    }

    @Test
    @Order(3)
    public void repositoryEntity_isTicket() {
        assertTrue(Arrays.stream(TicketRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[0].equals(Ticket.class)));
    }

    @Test
    @Order(3)
    public void repositoryId_isInteger() {
        assertTrue(Arrays.stream(TicketRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[1].equals(Integer.class)));
    }

    @Test
    @Order(4)
    public void repositoryFindAllUnsold_exists() {
        assertDoesNotThrow(() -> TicketRepository.class.getDeclaredMethod("findAllUnsold"));
    }

    @Test
    @Order(5)
    public void repositoryFindAllUnsold_returnsIterable() {
        try {
            assertTrue(Iterable.class.isAssignableFrom(TicketRepository.class.getDeclaredMethod("findAllUnsold").getReturnType()));
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    @Order(5)
    public void repositoryFindAllUnsold_returnsIterableOfTickets() {
        try {
            assertEquals(Ticket.class, ((ParameterizedType) TicketRepository.class.getDeclaredMethod("findAllUnsold").getGenericReturnType()).getActualTypeArguments()[0]);
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    @Order(6)
    public void repositoryFindAllUnsold_hasQueryAnnotation() {
        try {
            assertNotNull((TicketRepository.class.getDeclaredMethod("findAllUnsold").getAnnotation(Query.class)));
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    @Order(7)
    public void repositoryFindAllUnsold_isCorrectQuery() {
        try {
            assertTrue(TicketRepository.class.getDeclaredMethod("findAllUnsold").getAnnotation(Query.class).value().equalsIgnoreCase("select t from Ticket t where t.amount > 0"));
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

}
