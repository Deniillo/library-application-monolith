package itmo.deniill.service.services;

import itmo.deniill.dao.model.Role;
import itmo.deniill.dao.model.enums.RoleType;
import itmo.deniill.dao.repository.RoleRepository;
import itmo.deniill.service.services.interfaces.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("cant find role by id " + id));
    }

    @Override
    public Role findRoleByName(RoleType name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepository.findById(role.getId())
                .map(existingRole -> {
                    existingRole.setName(role.getName());
                    return roleRepository.save(existingRole);
                })
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + role.getId()));
    }

    @Override
    public void deleteRole(int id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        roleRepository.delete(role);
    }
}
