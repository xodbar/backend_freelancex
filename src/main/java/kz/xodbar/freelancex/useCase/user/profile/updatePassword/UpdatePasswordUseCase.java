package kz.xodbar.freelancex.useCase.user.profile.updatePassword;

import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.model.UserModel;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePasswordUseCase
        implements AuthenticatedOnlyUseCase<UpdatePasswordUseCaseInput, String, UpdatePasswordUseCaseOutput> {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UpdatePasswordUseCaseOutput handle(UpdatePasswordUseCaseInput input, String token) {

        logger.info("Starting update password for token: " + token);

        if (!jwtTokenProvider.tokenIsValid(token)) {
            logger.error("Failed update password for token: " + token);
            return new UpdatePasswordUseCaseOutput(
                    null,
                    null,
                    "Invalid token"
            );
        }

        User userDto = (User) jwtTokenProvider.getAuthentication(token).getPrincipal();

        if (userDto == null) {
            logger.error("Failed update password because of incorrect user data for token: " + token);
            return new UpdatePasswordUseCaseOutput(
                    null,
                    null,
                    "Incorrect user data"
            );
        }

        UserModel user = userService.updatePassword(
                input.getOldPassword(),
                input.getNewPassword(),
                userDto.getUsername()
        );

        if (user == null) {
            logger.error("Failed update password because of incorrect old password for token: " + token);
            return new UpdatePasswordUseCaseOutput(
                    null,
                    null,
                    "Incorrect old password"
            );
        }

        return new UpdatePasswordUseCaseOutput(
                jwtTokenProvider.generateToken(user.getUsername(), user.toDto().getRoles()),
                user.toDto(),
                null
        );
    }
}
