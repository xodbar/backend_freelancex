package kz.xodbar.freelancex.useCase.order.get.search;

import kz.xodbar.freelancex.core.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchOrderByTitleUseCaseOutput {
    private List<Order> orders;
    private String errorMessage;
}
