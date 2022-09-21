package kz.xodbar.freelancex.useCase.order.get.order;

import kz.xodbar.freelancex.core.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetAllOrdersAndOrderByUseCaseInput {
    @NonNull
    private OrderStatus status;
    @NonNull
    private OrderByEnum orderBy;
}
