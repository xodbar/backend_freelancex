package kz.xodbar.freelancex.useCase.order.get.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetSpecificOrderUseCaseInput {
    private Long orderId;
}
