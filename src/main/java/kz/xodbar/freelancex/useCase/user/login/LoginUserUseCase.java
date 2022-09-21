package kz.xodbar.freelancex.useCase.user.login;

import java.util.HashMap;
import java.util.Map;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.UseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserUseCase implements UseCase<LoginUserUseCaseInput, LoginUserUseCaseOutput> {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public LoginUserUseCaseOutput handle(LoginUserUseCaseInput input) {
        logger.info("Trying to log in by username: " + input.getUsername() + " and password: " + input.getPassword());

        LoginUserUseCaseOutput output = new LoginUserUseCaseOutput();

        try {

            String username = input.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, input.getPassword()));

            User user = userService.getByUsername(username);
            if (user == null)
                throw new UsernameNotFoundException("User with username " + username + " not found");

            String token = jwtTokenProvider.generateToken(username, user.getRoles());

            HashMap<String, String> result = new HashMap<>();
            result.put("username", username);
            result.put("token", token);

            output.setResult(result);

        } catch (AuthenticationException e) {
            logger.error("Failed to log in by username: " + input.getUsername()
                    + " and password: " + input.getPassword() + " because of authentication exception");

            e.printStackTrace();

            output.setResult(null);
            output.setErrorMessage("Invalid username or password");

            return output;
        } catch (Exception e) {
            logger.error("Failed to log in by username: " + input.getUsername()
                    + " and password: " + input.getPassword() + " because of exception");

            e.printStackTrace();

            output.setResult(null);
            output.setErrorMessage("Invalid username or password");
            return output;
        }

        return output;
    }
}
