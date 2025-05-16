package itmo.deniill.dao.repository;

import itmo.deniill.dao.model.Role;
import itmo.deniill.dao.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(RoleType name);
}
