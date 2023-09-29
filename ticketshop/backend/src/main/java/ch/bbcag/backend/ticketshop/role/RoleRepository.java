package ch.bbcag.backend.ticketshop.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select r from Role r join r.assignedPersons p where p.email = :email")
    List<Role> getRolesByUserEmail(@Param("email") String email);

    Role findRoleByName(String name);
}
