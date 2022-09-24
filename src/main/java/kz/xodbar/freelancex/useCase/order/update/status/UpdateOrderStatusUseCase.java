package kz.xodbar.freelancex.useCase.order.update.status;

import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.core.role.service.RoleService;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusUseCase
        implements AuthenticatedOnlyUseCase<UpdateOrderStatusUseCaseInput, String, UpdateOrderStatusUseCaseOutput> {

    private final OrderService orderService;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public UpdateOrderStatusUseCaseOutput handle(UpdateOrderStatusUseCaseInput input, String token) {

        UpdateOrderStatusUseCaseOutput output = new UpdateOrderStatusUseCaseOutput();
        String authorsUsername = jwtTokenProvider.getUsername(token);
        User user = userService.getByUsername(authorsUsername);

        try {
            logger.info("Updating order status: " + input.getNewStatus() + " for order id " + input.getOrderId());

            if (orderService.getById(input.getOrderId()) == null) {
                output.setErrorMessage("No such order!");
                throw new Exception("No such order! Id " + input.getOrderId());
            }

            Order order = orderService.getById(input.getOrderId());

            User contractor;

            if (order.getContractor() == null)
                contractor = null;
            else contractor = order.getContractor();
            
            Order updatedOrder = orderService.updateOrder(new Order(
                    order.getId(),
                    order.getTitle(),
                    order.getContent(),
                    order.getClient(),
                    contractor,
                    order.isActive(),
                    input.getNewStatus(),
                    order.getCreatedAt(),
                    order.getDeadline(),
                    order.getPrice(),
                    order.getField(),
                    order.getProposals()
            ));

            output.setResult(updatedOrder);
        } catch (Exception e) {
            logger.error("Error while updating status of order " + input.getOrderId() + " to " + input.getNewStatus()
                    + " by " + authorsUsername);
            e.printStackTrace();

            if (output.getErrorMessage() == null)
                output.setErrorMessage("Unexpected error: " + e.getMessage());
        }

        return output;
    }
}
