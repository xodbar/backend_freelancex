package kz.xodbar.freelancex.useCase.user.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginUserUseCaseInput {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
