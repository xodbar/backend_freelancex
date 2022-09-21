package kz.xodbar.freelancex.useCase.order.update.contractor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AssignContractorUseCaseInput {
    @NonNull
    private Long orderId;
    @NonNull
    private Long proposalId;
}
