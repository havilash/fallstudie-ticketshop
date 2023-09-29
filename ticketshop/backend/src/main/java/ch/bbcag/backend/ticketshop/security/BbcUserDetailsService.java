package ch.bbcag.backend.ticketshop.security;

import ch.bbcag.backend.ticketshop.person.Person;
import ch.bbcag.backend.ticketshop.person.PersonRepository;
import ch.bbcag.backend.ticketshop.role.AuthorityRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BbcUserDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    public BbcUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person optionalPerson = personRepository.findPersonByEmail(email);

        if (optionalPerson == null) {
            throw new UsernameNotFoundException("No user found");
        }

        final Set<AuthorityRole> authorityRoles = optionalPerson.getRoles().stream().map(AuthorityRole::new).collect(Collectors.toSet());
        return new User(optionalPerson.getEmail(), optionalPerson.getPassword(), authorityRoles);
    }
}
