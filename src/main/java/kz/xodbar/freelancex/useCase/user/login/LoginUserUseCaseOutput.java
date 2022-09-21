package kz.xodbar.freelancex.useCase.user.login;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginUserUseCaseOutput {
    private HashMap<String, String> result;
    private String errorMessage;
}
