package kz.xodbar.freelancex.useCase.user.profile.updatePassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePasswordUseCaseInput {
    private String oldPassword;
    private String newPassword;
}
