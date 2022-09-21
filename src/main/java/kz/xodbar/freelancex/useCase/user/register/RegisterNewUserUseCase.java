package kz.xodbar.freelancex.useCase.user.register;

import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.useCase.UseCase;
import kz.xodbar.freelancex.util.NullOrEmptyChecker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterNewUserUseCase implements UseCase<RegisterNewUserUseCaseInput, RegisterNewUserUseCaseOutput> {

    @Value("${utils.password.regexp}")
    private String passwordRegex;
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public RegisterNewUserUseCaseOutput handle(RegisterNewUserUseCaseInput input) {
        logger.info("Registering new user: " + input.toString());

        String[] params = {
                input.getUsername(),
                input.getName(),
                input.getSurname(),
                input.getPassword(),
                input.getPasswordRepetition(),
                input.getPhone(),
                input.getEmail()
        };

        if (!NullOrEmptyChecker.listDontContainNullOrEmptyString(params)) {
            logger.error("Failed to register new user: " + input + "\tCause: one of fields is empty");
            return new RegisterNewUserUseCaseOutput(
                    null,
                    "One of fields is empty!"
            );
        }

        if (!input.getPassword().equals(input.getPasswordRepetition())) {
            logger.error("Failed to register new user: " + input + "\tCause: password repetition aren't same");
            return new RegisterNewUserUseCaseOutput(
                    null,
                    "Password and repetition aren't same!"
            );
        }

        if (!passwordIsValid(input.getPassword())) {
            logger.error("Failed to register new user: " + input + "\tCause: invalid password");
            return new RegisterNewUserUseCaseOutput(
                    null,
                    """
                            Password is invalid. It should contain:
                            -At least 8 chars
                            -Contains at least one digit
                            -Contains at least one lower alpha char and one upper alpha char
                            -Contains at least one char within a set of special chars (@#%$^ etc.)
                            -Does not contain space, tab, etc."""
            );
        }

        if (userService.getByUsername(input.getUsername()) != null) {
            logger.error("Failed to register new user: " + input + "\tCause: username already taken");
            return new RegisterNewUserUseCaseOutput(
                    null,
                    ("Username " + input.getUsername() + " is already taken!")
            );
        }

        User newUser = userService.addUser(new User(
                null,
                input.getUsername(),
                input.getPassword(),
                input.getName(),
                input.getSurname(),
                input.getPhone(),
                input.getEmail(),
                null,
                null
        ));

        logger.info("New user: " + input + " registered successfully");

        return new RegisterNewUserUseCaseOutput(
                newUser.getUsername(),
                null
        );
    }

    private boolean passwordIsValid(String password) {
//        Pattern pattern = Pattern.compile(passwordRegex);
//        Matcher matcher = pattern.matcher(password);
        return true;
    }
}
