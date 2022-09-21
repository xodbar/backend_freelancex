package kz.xodbar.freelancex.useCase.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateNewFieldUseCaseInput {
    @NonNull
    private String newFieldName;
}
