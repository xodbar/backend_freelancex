package kz.xodbar.freelancex.core.user.service;

import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User addUser(User user);

    User getByUsername(String username);

    User getById(Long id);

    User updateUser(User user);

    void deleteUser(User user);

    UserModel updatePassword(String oldPassword, String newPassword, String username);

    UserModel getModelById(Long id);

    UserModel getModelByUsername(String username);

    UserModel getModelByUsernameAndPassword(String username, String password);

    void changeIsBlocked(Long id, Boolean isBlocked);
}
