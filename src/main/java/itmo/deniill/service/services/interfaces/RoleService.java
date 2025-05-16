package itmo.deniill.service.services.interfaces;


import itmo.deniill.dao.model.Role;
import itmo.deniill.dao.model.enums.RoleType;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    List<Role> findAllRoles();
    Role findRoleById(int id);
    Role findRoleByName(RoleType name);
    Role updateRole(Role role);
    void deleteRole(int id);
}
