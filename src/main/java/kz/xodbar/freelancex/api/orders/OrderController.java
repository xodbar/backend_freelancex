package kz.xodbar.freelancex.api.orders;

import kz.xodbar.freelancex.core.order.model.OrderStatus;
import kz.xodbar.freelancex.useCase.order.create.CreateNewOrderUseCase;
import kz.xodbar.freelancex.useCase.order.create.CreateNewOrderUseCaseInput;
import kz.xodbar.freelancex.useCase.order.create.CreateNewOrderUseCaseOutput;
import kz.xodbar.freelancex.useCase.order.get.filter.FilterOrderByUseCaseOutput;
import kz.xodbar.freelancex.useCase.order.get.filter.byField.FilterOrderByFieldUseCase;
import kz.xodbar.freelancex.useCase.order.get.filter.byField.FilterOrderByFieldUseCaseInput;
import kz.xodbar.freelancex.useCase.order.get.filter.byPrice.FilterOrderByPriceUseCase;
import kz.xodbar.freelancex.useCase.order.get.filter.byPrice.FilterOrderByPriceUseCaseInput;
import kz.xodbar.freelancex.useCase.order.get.order.*;
import kz.xodbar.freelancex.useCase.order.get.search.SearchOrderByTitleUseCase;
import kz.xodbar.freelancex.useCase.order.get.search.SearchOrderByTitleUseCaseOutput;
import kz.xodbar.freelancex.useCase.order.update.contractor.AssignContractorUseCase;
import kz.xodbar.freelancex.useCase.order.update.contractor.AssignContractorUseCaseInput;
import kz.xodbar.freelancex.useCase.order.update.contractor.AssignContractorUseCaseOutput;
import kz.xodbar.freelancex.useCase.order.update.status.UpdateOrderStatusUseCase;
import kz.xodbar.freelancex.useCase.order.update.status.UpdateOrderStatusUseCaseInput;
import kz.xodbar.freelancex.useCase.order.update.status.UpdateOrderStatusUseCaseOutput;
import kz.xodbar.freelancex.useCase.proposal.AddProposalUseCase;
import kz.xodbar.freelancex.useCase.proposal.AddProposalUseCaseInput;
import kz.xodbar.freelancex.useCase.proposal.AddProposalUseCaseOutput;
import kz.xodbar.freelancex.util.AuthenticationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final GetAllOrdersAndOrderByUseCase getAllOrdersAndOrderByUseCase;

    private final FilterOrderByPriceUseCase filterOrderByPriceUseCase;

    private final FilterOrderByFieldUseCase filterOrderByFieldUseCase;

    private final CreateNewOrderUseCase createNewOrderUseCase;

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    private final AssignContractorUseCase assignContractorUseCase;

    private final AddProposalUseCase addProposalUseCase;

    private final GetSpecificOrderUseCase getSpecificOrderUseCase;

    private final SearchOrderByTitleUseCase searchOrderByTitleUseCase;

    @GetMapping
    public ResponseEntity<GetAllOrdersAndOrderByUseCaseOutput> getAllActiveOrders() {
        GetAllOrdersAndOrderByUseCaseOutput result =
                getAllOrdersAndOrderByUseCase.handle(new GetAllOrdersAndOrderByUseCaseInput(
                        OrderStatus.ACTIVE,
                        OrderByEnum.ORDER_BY_CREATED_AT_DESC
                ));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/allFinished")
    public ResponseEntity<GetAllOrdersAndOrderByUseCaseOutput> getAllOrders() {
        GetAllOrdersAndOrderByUseCaseOutput result =
                getAllOrdersAndOrderByUseCase.handle(new GetAllOrdersAndOrderByUseCaseInput(
                        OrderStatus.FINISHED,
                        OrderByEnum.ORDER_BY_CREATED_AT_DESC
                ));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSpecificOrderUseCaseOutput> getOrder(@PathVariable Long id, HttpServletRequest request) {

        String token = AuthenticationResolver.getAuthToken(request);

        if (token == null)
            return ResponseEntity.ok(new GetSpecificOrderUseCaseOutput(
                    null,
                    null,
                    null,
                    "Token is not present"
            ));

        GetSpecificOrderUseCaseOutput output = getSpecificOrderUseCase.handle(new GetSpecificOrderUseCaseInput(id), token);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/search")
    public ResponseEntity<SearchOrderByTitleUseCaseOutput> searchOrders(
            HttpServletRequest request,
            String query
    ) {
        String token = AuthenticationResolver.getAuthToken(request);

        if (token == null)
            return ResponseEntity.ok(new SearchOrderByTitleUseCaseOutput(
                    null,
                    "Incorrect token. Try log in again"
            ));

        SearchOrderByTitleUseCaseOutput output = searchOrderByTitleUseCase.handle(query, AuthenticationResolver.getAuthToken(request));
        return ResponseEntity.ok(output);
    }

    @PostMapping("/orderBy")
    public ResponseEntity<GetAllOrdersAndOrderByUseCaseOutput> getAllOrdersAndOrderByParam(
            @Validated @RequestBody GetAllOrdersAndOrderByUseCaseInput body
    ) {
        return ResponseEntity.ok(getAllOrdersAndOrderByUseCase.handle(body));
    }

    @PostMapping("/filter/byPrice")
    public ResponseEntity<FilterOrderByUseCaseOutput> filterOrdersByPrice(
            @Validated @RequestBody FilterOrderByPriceUseCaseInput body
    ) {
        return ResponseEntity.ok(filterOrderByPriceUseCase.handle(body));
    }

    @PostMapping("/filter/byField")
    public ResponseEntity<FilterOrderByUseCaseOutput> filterOrdersByField(
            @Validated @RequestBody FilterOrderByFieldUseCaseInput body
    ) {
        return ResponseEntity.ok(filterOrderByFieldUseCase.handle(body));
    }

    @PostMapping("/create")
    public ResponseEntity<CreateNewOrderUseCaseOutput> createNewOrder(
            @Validated @RequestBody CreateNewOrderUseCaseInput body,
            HttpServletRequest request
    ) {
        String token = AuthenticationResolver.getAuthToken(request);

        if (token == null)
            return ResponseEntity.ok(new CreateNewOrderUseCaseOutput(
                    null,
                    "Token is not present"
            ));

        return ResponseEntity.ok(createNewOrderUseCase.handle(body, token));
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<UpdateOrderStatusUseCaseOutput> updateOrderStatus(
            @Validated @RequestBody UpdateOrderStatusUseCaseInput body,
            HttpServletRequest request
    ) {
        String token = AuthenticationResolver.getAuthToken(request);

        if (token == null)
            return ResponseEntity.ok(new UpdateOrderStatusUseCaseOutput(null, "Token is not present"));

        return ResponseEntity.ok(updateOrderStatusUseCase.handle(body, token));
    }

    @PutMapping("/assignContractor")
    public ResponseEntity<AssignContractorUseCaseOutput> assignContractor(
            @Validated @RequestBody AssignContractorUseCaseInput body,
            HttpServletRequest request
    ) {
        String token = AuthenticationResolver.getAuthToken(request);
        if (token == null)
            return ResponseEntity.ok(new AssignContractorUseCaseOutput(
                    null,
                    "Token is not present"
            ));

        return ResponseEntity.ok(assignContractorUseCase.handle(body, token));
    }

    @PostMapping("/addProposal")
    public ResponseEntity<AddProposalUseCaseOutput> addProposal(
            @Validated @RequestBody AddProposalUseCaseInput body,
            HttpServletRequest request
    ) {
        String token = AuthenticationResolver.getAuthToken(request);

        if (token == null)
            return ResponseEntity.ok(new AddProposalUseCaseOutput(
                    null,
                    "Token is not preset"
            ));

        return ResponseEntity.ok(addProposalUseCase.handle(body, token));
    }
}
