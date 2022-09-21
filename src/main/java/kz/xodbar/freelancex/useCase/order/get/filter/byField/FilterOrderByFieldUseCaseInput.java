package kz.xodbar.freelancex.useCase.order.get.filter.byField;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FilterOrderByFieldUseCaseInput {
    @NonNull
    private String fieldName;
}
