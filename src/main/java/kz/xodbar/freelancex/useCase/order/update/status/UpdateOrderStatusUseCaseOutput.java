package kz.xodbar.freelancex.useCase.order.update.status;

import kz.xodbar.freelancex.core.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateOrderStatusUseCaseOutput {
    private Order result;
    private String errorMessage;
}
