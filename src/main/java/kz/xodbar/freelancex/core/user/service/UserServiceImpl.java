package kz.xodbar.freelancex.core.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kz.xodbar.freelancex.core.role.model.RoleModel;
import kz.xodbar.freelancex.core.role.service.RoleService;
import kz.xodbar.freelancex.core.user.UserRepository;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder defaultPasswordEncoder;
    private final RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(UserModel::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User addUser(User user) {
        try {
            logger.info("Adding new user: " + user.toString());

            String encryptedPassword = defaultPasswordEncoder.encode(user.getPassword());
            return userRepository.save(new UserModel(
                    null,
                    user.getUsername(),
                    encryptedPassword,
                    user.getName(),
                    user.getSurname(),
                    user.getPhone(),
                    user.getEmail(),
                    false,
                    getDefaultRoles()
            )).toDto();
        } catch (Exception e) {
            logger.error("Error while adding new user: " + user.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public User getByUsername(String username) {
        try {
            logger.info("Getting user by username: " + username);
            return userRepository.findByUsername(username).toDto();
        } catch (Exception e) {
            logger.error("Error while getting user by username: " + username);
            return null;
        }
    }

    @Override
    @Transactional
    public User getById(Long id) {
        try {
            logger.info("Getting user by id: " + id);
            return userRepository
                    .findById(id)
                    .orElseThrow()
                    .toDto();
        } catch (Exception e) {
            logger.error("Error while getting user by id: " + id);
            return null;
        }
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        try {
            logger.info("Updating user: " + user.getUsername());

            List<RoleModel> roleModels = new ArrayList<>();

            user.getRoles().forEach(roleDto -> roleModels.add(new RoleModel(roleDto.getId(), roleDto.getRole())));

            userRepository.save(new UserModel(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getName(),
                    user.getSurname(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getIsBlocked(),
                    roleModels
            ));

            return userRepository.getReferenceById(user.getId()).toDto();
        } catch (Exception e) {
            logger.error("Error while updating user: " + user.getUsername());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        try {
            logger.info("Deleting user: " + user.getUsername());
            userRepository.deleteById(user.getId());
        } catch (Exception e) {
            logger.error("Error while deleting user: " + user.getUsername());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public UserModel getModelById(Long id) {
        try {
            logger.info("Getting user model by id: " + id);
            return userRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            logger.error("Error while getting user model by id: " + id);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public UserModel getModelByUsername(String username) {
        try {
            logger.info("Getting user model by username: " + username);
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            logger.error("Error while getting user model by username: " + username);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public UserModel getModelByUsernameAndPassword(String username, String password) {
        try {
            logger.info("Getting user model by username and password: " + username + ", " + password);

            String encryptedPassword = defaultPasswordEncoder.encode(password);

            System.out.println("username " + username + " password " + encryptedPassword);

            return userRepository.findByUsernameAndPassword(username, encryptedPassword);
        } catch (Exception e) {
            logger.error("Error while getting model by username and password: " + username + ", " + password);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void changeIsBlocked(Long id, Boolean isBlocked) {
        UserModel model = userRepository.findById(id).orElseThrow();
        model.setIsBlocked(isBlocked);
        userRepository.save(model);
    }

    private List<RoleModel> getDefaultRoles() {
        return roleService.getDefaultRoleModels();
    }
}
