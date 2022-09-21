package kz.xodbar.freelancex.useCase.proposal;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddProposalUseCaseInput {
    @NonNull
    private Long orderId;
    @NonNull
    private Integer proposedPrice;
}
