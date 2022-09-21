package kz.xodbar.freelancex.useCase.order.get.order;

import java.util.List;
import kz.xodbar.freelancex.core.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllOrdersAndOrderByUseCaseOutput {
    private List<Order> orders;
    private String errorMessage;
}
