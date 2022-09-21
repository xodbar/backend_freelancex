package kz.xodbar.freelancex.useCase.order.get.order;

import java.util.List;
import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.useCase.UseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllOrdersAndOrderByUseCase implements UseCase<GetAllOrdersAndOrderByUseCaseInput, GetAllOrdersAndOrderByUseCaseOutput> {

    private final OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public GetAllOrdersAndOrderByUseCaseOutput handle(GetAllOrdersAndOrderByUseCaseInput input) {
        GetAllOrdersAndOrderByUseCaseOutput output = new GetAllOrdersAndOrderByUseCaseOutput();

        try {
            logger.info("Getting all active orders and ordering type is " + input.getOrderBy());

            List<Order> orders = orderService.getAllByStatusAndOrder(input.getStatus(), input.getOrderBy());
            if (orders == null || orders.isEmpty())
                throw new Exception("No active orders");

            logger.info("Finished getting all active orders and ordering type is " + input.getOrderBy());
            output.setOrders(orders);
        } catch (Exception e) {
            logger.error("Failed getting all active orders and ordering type is " + input.getOrderBy());
            e.printStackTrace();
            output.setErrorMessage("Failed to get orders. Cause: " + e.getMessage());
        }

        return output;
    }
}
