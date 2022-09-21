package kz.xodbar.freelancex.useCase.user.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterNewUserUseCaseOutput {
    private String username;
    private String errorMessage;
}
