package ch.bbcag.backend.ticketshop.role;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRolesByUserEmail(String email) {
        return roleRepository.getRolesByUserEmail(email);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
