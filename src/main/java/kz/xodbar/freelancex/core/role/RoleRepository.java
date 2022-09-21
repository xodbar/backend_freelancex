package kz.xodbar.freelancex.core.role;

import kz.xodbar.freelancex.core.role.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    RoleModel findByRole(String role);
}
