package kz.xodbar.freelancex.useCase.user.admin.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdminBlockUserUseCaseInput {
    @NonNull
    private Long id;
    @NonNull
    private Boolean isBlocked;
}
