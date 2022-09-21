package kz.xodbar.freelancex.useCase.user.admin.block;

import kz.xodbar.freelancex.core.role.service.RoleService;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBlockUserUseCase
        implements AuthenticatedOnlyUseCase<AdminBlockUserUseCaseInput, String, AdminBlockUserUseCaseOutput> {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public AdminBlockUserUseCaseOutput handle(AdminBlockUserUseCaseInput input, String token) {
        AdminBlockUserUseCaseOutput output = new AdminBlockUserUseCaseOutput();
        String authorsUsername = jwtTokenProvider.getUsername(token);
        User user = userService.getByUsername(authorsUsername);

        try {

            if (!user.getRoles().contains(roleService.getByRoleName("ROLE_ADMIN"))) {
                output.setErrorMessage("You have no permission to create new field!");
                throw new Exception("User " + user.getUsername() + " have no admin permissions to create fields!");
            }

            logger.info("Blocking user " + input.getId() + " by " + authorsUsername);

            userService.changeIsBlocked(input.getId(), input.getIsBlocked());


            output.setIsBlockedUpdated(userService.getById(input.getId()).getIsBlocked());

        } catch (Exception e) {
            logger.info("Failed to block user id: " + input.getId() + " by " + authorsUsername);
            e.printStackTrace();

            if (output.getErrorMessage() == null)
                output.setErrorMessage("Unexpected error: " + e.getMessage());
        }

        return output;
    }
}
