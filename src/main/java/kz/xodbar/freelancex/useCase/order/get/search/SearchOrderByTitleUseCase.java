package kz.xodbar.freelancex.useCase.order.get.search;

import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.useCase.AuthenticatedOnlyUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchOrderByTitleUseCase implements AuthenticatedOnlyUseCase<String, String, SearchOrderByTitleUseCaseOutput> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderService orderService;

    @Override
    public SearchOrderByTitleUseCaseOutput handle(String input, String token) {
        try {
            logger.info("Searching orders by query: " + input);

            if (orderService.searchByTitle(input) == null)
                return new SearchOrderByTitleUseCaseOutput(
                        null,
                        "No such orders. Try another query"
                );

            return new SearchOrderByTitleUseCaseOutput(
                    orderService.searchByTitle(input),
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new SearchOrderByTitleUseCaseOutput(
                    null,
                    "Unhandled exception while searching order. Try again later"
            );
        }
    }
}
