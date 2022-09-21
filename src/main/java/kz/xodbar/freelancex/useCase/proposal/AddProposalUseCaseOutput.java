package kz.xodbar.freelancex.useCase.proposal;

import kz.xodbar.freelancex.core.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddProposalUseCaseOutput {
    private Order changedOrder;
    private String errorMessage;
}
