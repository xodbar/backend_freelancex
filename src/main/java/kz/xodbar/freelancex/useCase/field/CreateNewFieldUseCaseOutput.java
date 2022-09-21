package kz.xodbar.freelancex.useCase.field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateNewFieldUseCaseOutput {
    private Long newFieldId;
    private String errorMessage;
}
