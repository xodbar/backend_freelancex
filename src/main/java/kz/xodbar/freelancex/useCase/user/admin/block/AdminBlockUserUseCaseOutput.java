package kz.xodbar.freelancex.useCase.user.admin.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminBlockUserUseCaseOutput {
    private Boolean isBlockedUpdated;
    private String errorMessage;
}
