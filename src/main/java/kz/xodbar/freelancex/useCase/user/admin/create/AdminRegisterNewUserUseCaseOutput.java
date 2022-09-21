package kz.xodbar.freelancex.useCase.user.admin.create;

import kz.xodbar.freelancex.core.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdminRegisterNewUserUseCaseOutput {
    private User newUser;
    private String errorMessage;
}
