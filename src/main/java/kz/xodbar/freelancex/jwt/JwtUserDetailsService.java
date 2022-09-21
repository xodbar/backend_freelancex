package kz.xodbar.freelancex.jwt;

import kz.xodbar.freelancex.core.user.model.UserModel;
import kz.xodbar.freelancex.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(noRollbackFor = UsernameNotFoundException.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Trying to load user by username: " + username);

        UserModel userModel = userService.getModelByUsername(username);

        if (userModel == null)
            throw new UsernameNotFoundException("No such user: " + username);

        logger.info("User by username: " + username + " was found successfully");

        return JwtUserFactory.createJwtUser(userModel);
    }
}
