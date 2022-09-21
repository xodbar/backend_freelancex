package kz.xodbar.freelancex.useCase.order.get.filter.byPrice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FilterOrderByPriceUseCaseInput {
    @NonNull
    private Integer minPrice;
    @NonNull
    private Integer maxPrice;
}
