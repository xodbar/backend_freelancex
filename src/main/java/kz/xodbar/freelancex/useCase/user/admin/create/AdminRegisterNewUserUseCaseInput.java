package kz.xodbar.freelancex.useCase.user.admin.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdminRegisterNewUserUseCaseInput {
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String passwordRepetition;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String phone;
    @NonNull
    private String email;

    @Override
    public String toString() {
        return "username: " + username + " | password: " + password + " | passwordRepetition: " + passwordRepetition +
                " | name: " + name + " | surname: " + surname;
    }
}
