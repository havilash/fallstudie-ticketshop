package ch.bbcag.backend.ticketshop.person;

import ch.bbcag.backend.ticketshop.FailedValidationException;
import ch.bbcag.backend.ticketshop.role.Role;
import ch.bbcag.backend.ticketshop.role.RoleService;
import ch.bbcag.backend.ticketshop.security.AuthRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personRepository = personRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public PersonResponseDTO create(AuthRequestDTO authRequestDTO) {
        Person person = new Person();
        person.setPassword(bCryptPasswordEncoder.encode(authRequestDTO.getPassword()));
        person.setEmail(authRequestDTO.getEmail());
        return PersonMapper.toDTO(personRepository.save(person));
    }

    public PersonResponseDTO update(PersonRequestDTO personDTO, Integer personId) {
        Person existingPerson = personRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
        mergePerson(existingPerson, PersonMapper.fromDTO(personDTO));
        return PersonMapper.toDTO(personRepository.save(existingPerson));
    }

    public PersonResponseDTO assignRole(String roleName, Integer personId) {
        Person person = personRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
        Set<Role> newRoles = person.getRoles();
        Role newRole = roleService.findRoleByName(roleName);
        if (newRole == null) { throw new EntityNotFoundException(); }
        newRoles.add(newRole);
        person.setRoles(newRoles);
        return PersonMapper.toDTO(personRepository.save(person));
    }

    public PersonResponseDTO removeRole(String roleName, Integer personId) {
        Person person = personRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
        Set<Role> newRoles = person.getRoles();
        Role newRole = roleService.findRoleByName(roleName);
        if (newRole == null) { throw new EntityNotFoundException(); }
        newRoles.remove(newRole);
        person.setRoles(newRoles);
        return PersonMapper.toDTO(personRepository.save(person));
    }

    public PersonResponseDTO findById(Integer id) {
        return PersonMapper.toDTO(personRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public Boolean existsByEmail(String email) {
        return personRepository.existsByEmail(email);
    }

    public PersonResponseDTO findByEmail(String email) {
        return PersonMapper.toDTO(personRepository.findPersonByEmail(email));
    }

    private void mergePerson(Person existing, Person changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getEmail() != null) {
            if (StringUtils.isNotBlank(changing.getEmail())) {
                existing.setEmail(changing.getEmail());
            } else {
                errors.put("email", List.of("email must not be empty"));
            }
        }
        if (changing.getPassword() != null) {
            existing.setPassword(changing.getPassword());
        }
        if (changing.getEvents() != null) {
            existing.setEvents(changing.getEvents());
        }

        if (!errors.isEmpty()) { throw new FailedValidationException(errors); }
    }
}
