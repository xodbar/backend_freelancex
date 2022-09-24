package kz.xodbar.freelancex.useCase.order.create;

import kz.xodbar.freelancex.core.field.model.Field;
import kz.xodbar.freelancex.core.field.service.FieldService;
import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.order.model.OrderStatus;
import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CreateNewOrderUseCase
        implements AuthenticatedOnlyUseCase<CreateNewOrderUseCaseInput, String, CreateNewOrderUseCaseOutput> {

    private final OrderService orderService;

    private final JwtTokenProvider jwtTokenProvider;

    private final FieldService fieldService;

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public CreateNewOrderUseCaseOutput handle(CreateNewOrderUseCaseInput input, String token) {

        CreateNewOrderUseCaseOutput output = new CreateNewOrderUseCaseOutput();
        String authorsUsername = jwtTokenProvider.getUsername(token);
        User user = userService.getByUsername(authorsUsername);

        try {

            logger.info("Creating new order " + input + " by " + authorsUsername);

            if (orderService.orderAlreadyExists(input.getTitle())) {
                output.setErrorMessage("Order with this title is already exists!");
                throw new Exception("Order already exists (title)");
            }

            if (input.getTitle().isBlank() || input.getContent().isBlank()) {
                output.setErrorMessage("One of fields (title, content) is empty! Please, fill all fields");
                throw new Exception("One of fields (title, content) is empty! Please, fill all fields");
            }

            if (input.getDeadline().isBefore(LocalDateTime.now())) {
                output.setErrorMessage("Order's deadline can not be later than now!");
                throw new Exception("Order's deadline can not be later than now!");
            }

            if (input.getPrice() < 500) {
                output.setErrorMessage("Order's price can not be less than 500! Please, increase payment amount");
                throw new Exception("Order's price can not be less than 500! Please, increase payment amount");
            }

            if (fieldService.getByName(input.getFieldName()) == null) {
                output.setErrorMessage("Order's field doesn't exists!");
                throw new Exception("Order's field doesn't exists!");
            }

            String field = fieldService.getByName(input.getFieldName()).getName();

            Order createdOrder = orderService.createOrder(new Order(
                    null,
                    input.getTitle(),
                    input.getContent(),
                    user,
                    null,
                    true,
                    OrderStatus.ACTIVE,
                    LocalDateTime.now(),
                    input.getDeadline(),
                    input.getPrice(),
                    field,
                    new ArrayList<>()
            ));

            output.setCreatedOrder(createdOrder);

        } catch (Exception e) {
            logger.info("Failed to create new order: " + input + " by " + authorsUsername);
            e.printStackTrace();

            if (output.getErrorMessage() == null)
                output.setErrorMessage("Unexpected error: " + e.getMessage());
        }

        return output;
    }
}
