package kz.xodbar.freelancex.useCase.order.update.contractor;

import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.order.model.OrderStatus;
import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.core.proposal.model.Proposal;
import kz.xodbar.freelancex.core.proposal.service.ProposalService;
import kz.xodbar.freelancex.core.role.service.RoleService;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AssignContractorUseCase
        implements AuthenticatedOnlyUseCase<AssignContractorUseCaseInput, String, AssignContractorUseCaseOutput> {

    private final OrderService orderService;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final ProposalService proposalService;

    private final RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public AssignContractorUseCaseOutput handle(AssignContractorUseCaseInput input, String token) {
        AssignContractorUseCaseOutput output = new AssignContractorUseCaseOutput();
        String authorsUsername = jwtTokenProvider.getUsername(token);

        User client = userService.getByUsername(authorsUsername);
        Proposal proposal = proposalService.getProposalById(input.getProposalId());
        User contractor = userService.getById(proposal.getCandidate().getId());
        Order order = orderService.getById(input.getOrderId());

        try {

            if (!Objects.equals(order.getClient().getUsername(), client.getUsername())
                    || client.getRoles().contains(roleService.getById(1L))) {
                output.setErrorMessage("You have no permission to assign contractor to the order!");
                throw new Exception("User " + authorsUsername + " is not a client of an order " + order.getId());
            }

            logger.info("Assigning to order " + input.getOrderId() + " proposal " + input.getProposalId());

            Order updatedOrder = orderService.updateOrder(
                    new Order(
                            order.getId(),
                            order.getTitle(),
                            order.getContent(),
                            order.getClient(),
                            contractor,
                            order.isActive(),
                            OrderStatus.IN_PROCESS,
                            order.getCreatedAt(),
                            order.getDeadline(),
                            proposal.getProposedPrice(),
                            order.getField(),
                            order.getProposals()
                    )
            );

            output.setChangedOrder(updatedOrder);
        } catch (Exception e) {
            logger.error("Failed to assign contractor to " + order.getId());
            e.printStackTrace();

            if (output.getErrorMessage() == null)
                output.setErrorMessage("Unexpected error: " + e.getMessage());
        }

        return output;
    }
}
