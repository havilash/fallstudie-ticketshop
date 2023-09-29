package ch.bbcag.backend.ticketshop.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findPersonByEmail(@Param("email") String email);

    Boolean existsByEmail(@Param("email") String email);
}
