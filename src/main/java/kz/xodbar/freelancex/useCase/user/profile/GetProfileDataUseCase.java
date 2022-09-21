package kz.xodbar.freelancex.useCase.user.profile;

import kz.xodbar.freelancex.core.role.model.Role;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetProfileDataUseCase
        implements AuthenticatedOnlyUseCase<HttpServletRequest, String, GetProfileDataUseCaseOutput> {

    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public GetProfileDataUseCaseOutput handle(HttpServletRequest input, String token) {

        if (!input.getHeader("Authorization").substring(7).equals(token))
            return new GetProfileDataUseCaseOutput(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "Tokens aren't same!"
            );

        try {
            String authorsUsername = jwtTokenProvider.getUsername(token);
            logger.info("Getting profile data for " + authorsUsername);

            User user = userService.getByUsername(authorsUsername);

            return new GetProfileDataUseCaseOutput(
                    user.getUsername(),
                    user.getName(),
                    user.getSurname(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getRoles().stream()
                            .map(Role::getRole)
                            .collect(Collectors.toList()),
                    null
            );
        } catch (Exception e) {
            logger.error("Error while getting profile data for token " + token);
            e.printStackTrace();
            return new GetProfileDataUseCaseOutput(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "Error was occurred while getting profile data. Cause: " + e.getMessage()
            );
        }
    }
}
