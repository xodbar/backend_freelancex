package kz.xodbar.freelancex.useCase.order.get.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchOrderByTitleUseCaseInput {
    private String query;
}
