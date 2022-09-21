package kz.xodbar.freelancex.useCase.field;

import kz.xodbar.freelancex.core.field.model.Field;
import kz.xodbar.freelancex.core.field.service.FieldService;
import kz.xodbar.freelancex.core.role.service.RoleService;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CreateNewFieldUseCase
        implements AuthenticatedOnlyUseCase<CreateNewFieldUseCaseInput, String, CreateNewFieldUseCaseOutput> {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final FieldService fieldService;

    private final RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public CreateNewFieldUseCaseOutput handle(CreateNewFieldUseCaseInput input, String token) {
        CreateNewFieldUseCaseOutput output = new CreateNewFieldUseCaseOutput();
        String authorsUsername = jwtTokenProvider.getUsername(token);
        User user = userService.getByUsername(authorsUsername);

        try {

            if (!user.getRoles().contains(roleService.getByRoleName("ROLE_ADMIN"))) {
                output.setErrorMessage("You have no permission to create new field!");
                throw new Exception("User " + user.getUsername() + " have no admin permissions to create fields!");
            }

            logger.info("Creating new field " + input.getNewFieldName() + " by " + authorsUsername);

            if (fieldService.fieldAlreadyExists(input.getNewFieldName())) {
                output.setErrorMessage("Field with this name is already exists!");
                throw new Exception("Field already exists (name)");
            }

            Field createdField = fieldService.createField(new Field(
                    null,
                    input.getNewFieldName(),
                    new ArrayList<>()
            ));

            output.setNewFieldId(createdField.getId());

        } catch (Exception e) {
            logger.info("Failed to create new field: " + input + " by " + authorsUsername);
            e.printStackTrace();

            if (output.getErrorMessage() == null)
                output.setErrorMessage("Unexpected error: " + e.getMessage());
        }

        return output;
    }
}
