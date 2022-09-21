package kz.xodbar.freelancex.api.orders.response;

import kz.xodbar.freelancex.core.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetOrderByIdResponse {
    private Order result;
    private String errorMessage;
}
