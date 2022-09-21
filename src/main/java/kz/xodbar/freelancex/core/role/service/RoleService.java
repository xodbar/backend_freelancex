package kz.xodbar.freelancex.core.role.service;

import java.util.List;
import kz.xodbar.freelancex.core.role.model.Role;
import kz.xodbar.freelancex.core.role.model.RoleModel;

public interface RoleService {
    Role getById(Long id);
    Role getByRoleName(String roleName);
    List<Role> getDefaultRoles();

    List<RoleModel> getDefaultRoleModels();
}
