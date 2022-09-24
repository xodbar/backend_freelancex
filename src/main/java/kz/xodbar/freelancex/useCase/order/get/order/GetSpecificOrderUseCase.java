package kz.xodbar.freelancex.useCase.order.get.order;

import io.swagger.annotations.Authorization;
import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.core.role.service.RoleService;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import kz.xodbar.freelancex.useCase.UseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetSpecificOrderUseCase implements AuthenticatedOnlyUseCase<GetSpecificOrderUseCaseInput, String, GetSpecificOrderUseCaseOutput> {

    private final OrderService orderService;

    private final JwtTokenProvider tokenProvider;

    private final RoleService roleService;

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public GetSpecificOrderUseCaseOutput handle(GetSpecificOrderUseCaseInput input, String token) {
        GetSpecificOrderUseCaseOutput output = new GetSpecificOrderUseCaseOutput();

        try {
            logger.info("Getting order by id " + input.getOrderId());

            Order order = orderService.getById(input.getOrderId());
            if (order == null)
                return new GetSpecificOrderUseCaseOutput(
                        null,
                        null,
                        null,
                        "No order by this id"
                );

            String requestedUsername = tokenProvider.getUsername(token);

            User requestedUser = userService.getByUsername(requestedUsername);

            output.setHasAccessToUpdate(requestedUser.getUsername().equals(order.getClient().getUsername())
                    || isAdminRole(requestedUser) || requestedUsername.equals("admin"));

            output.setRequestedUserUsername(requestedUsername);

            logger.info("Finished getting order by id " + input.getOrderId());
            output.setOrder(order);
        } catch (Exception e) {
            logger.error("Failed getting order by id " + input.getOrderId());
            e.printStackTrace();
            output.setErrorMessage("Failed to get orders. Cause: " + e.getMessage());
        }

        return output;
    }

    private boolean isAdminRole(User user) {
        return user.getRoles().contains(roleService.getById(1L));
    }
}
