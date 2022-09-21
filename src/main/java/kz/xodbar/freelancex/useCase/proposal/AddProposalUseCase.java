package kz.xodbar.freelancex.useCase.proposal;

import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.core.proposal.model.Proposal;
import kz.xodbar.freelancex.core.proposal.service.ProposalService;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.jwt.JwtTokenProvider;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AddProposalUseCase
        implements AuthenticatedOnlyUseCase<AddProposalUseCaseInput, String, AddProposalUseCaseOutput> {

    private final UserService userService;

    private final ProposalService proposalService;

    private final JwtTokenProvider jwtTokenProvider;

    private final OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public AddProposalUseCaseOutput handle(AddProposalUseCaseInput input, String token) {
        AddProposalUseCaseOutput output = new AddProposalUseCaseOutput();
        String authorsUsername = jwtTokenProvider.getUsername(token);
        User candidate = userService.getByUsername(authorsUsername);

        try {
            logger.info("Adding proposal to order " + input.getOrderId()
                    + " by " + authorsUsername + " with price " + input.getProposedPrice());

            if (orderService.getById(input.getOrderId()) == null) {
                output.setErrorMessage("No such order!");
                throw new Exception("No such order by id " + input.getOrderId());
            }

            if (input.getProposedPrice() < 500) {
                output.setErrorMessage("Proposed price can not be less than 500!");
                throw new Exception("Proposed price is less than 500 in order id "
                        + input.getOrderId() + " by " + authorsUsername);
            }

            Proposal proposal = proposalService.createProposal(new Proposal(
                    null,
                    candidate,
                    orderService.getById(input.getOrderId()),
                    input.getProposedPrice(),
                    LocalDateTime.now()
            ));

            output.setChangedOrder(orderService.getById(proposal.getOrder().getId()));
        } catch (Exception e) {
            logger.error("Error while adding proposal to order " + input.getOrderId() + " by " + authorsUsername);
            e.printStackTrace();

            if (output.getErrorMessage() == null)
                output.setErrorMessage("Unexpected error: " + e.getMessage());
        }

        return output;
    }
}
