package kz.xodbar.freelancex.useCase.order.get.order;

import kz.xodbar.freelancex.core.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetSpecificOrderUseCaseOutput {
    private Order order;
    private Boolean hasAccessToUpdate;
    private String requestedUserUsername;
    private String errorMessage;
}
