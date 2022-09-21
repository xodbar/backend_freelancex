package kz.xodbar.freelancex.core.user;

import kz.xodbar.freelancex.core.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);

    UserModel findByUsernameAndPassword(String username, String password);
}
