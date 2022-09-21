package kz.xodbar.freelancex.core.role.service;

import java.util.Collections;
import java.util.List;
import kz.xodbar.freelancex.core.role.RoleRepository;
import kz.xodbar.freelancex.core.role.model.Role;
import kz.xodbar.freelancex.core.role.model.RoleModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Role getById(Long id) {
        try {
            logger.info("Getting role by id:" + id);
            return roleRepository.findById(id).orElseThrow().toDto();
        } catch (Exception e) {
            logger.error("Error while getting role by id: " + id);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Role getByRoleName(String roleName) {
        try {
            logger.info("Getting role by role name:" + roleName);
            return roleRepository.findByRole(roleName).toDto();
        } catch (Exception e) {
            logger.error("Error while getting role by role name: " + roleName);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Role> getDefaultRoles() {
        try {
            logger.info("Getting default roles (dto)");
            return Collections.singletonList(roleRepository.findByRole("ROLE_USER").toDto());
        } catch (Exception e) {
            logger.error("Error while getting roles (dto)");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<RoleModel> getDefaultRoleModels() {
        try {
            logger.info("Getting all default roles");
            return Collections.singletonList(roleRepository.findByRole("ROLE_USER"));
        } catch (Exception e) {
            logger.error("Error while getting roles");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
