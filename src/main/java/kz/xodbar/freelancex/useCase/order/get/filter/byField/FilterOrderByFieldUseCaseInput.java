package kz.xodbar.freelancex.useCase.order.get.filter.byField;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilterOrderByFieldUseCaseInput {
    @NonNull
    private String fieldName;
}
