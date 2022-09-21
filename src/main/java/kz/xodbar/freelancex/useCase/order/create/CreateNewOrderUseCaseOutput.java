package kz.xodbar.freelancex.useCase.order.create;

import kz.xodbar.freelancex.core.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateNewOrderUseCaseOutput {
    private Order createdOrder;
    private String errorMessage;
}
