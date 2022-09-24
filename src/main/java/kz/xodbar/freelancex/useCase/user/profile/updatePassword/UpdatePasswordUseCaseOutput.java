package kz.xodbar.freelancex.useCase.user.profile.updatePassword;

import kz.xodbar.freelancex.core.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePasswordUseCaseOutput {
    private String newToken;
    private User user;
    private String errorMessage;
}
