package kz.xodbar.freelancex.useCase.order.update.contractor;

import kz.xodbar.freelancex.core.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AssignContractorUseCaseOutput {
    private Order changedOrder;
    private String errorMessage;
}
