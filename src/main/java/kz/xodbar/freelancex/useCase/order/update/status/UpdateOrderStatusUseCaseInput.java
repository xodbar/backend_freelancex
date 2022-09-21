package kz.xodbar.freelancex.useCase.order.update.status;

import kz.xodbar.freelancex.core.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateOrderStatusUseCaseInput {
    @NonNull
    private Long orderId;
    @NonNull
    private OrderStatus newStatus;
}
