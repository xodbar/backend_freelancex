package kz.xodbar.freelancex.useCase.order.get.filter.byPrice;

import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.useCase.UseCase;
import kz.xodbar.freelancex.useCase.order.get.filter.FilterOrderByUseCaseOutput;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterOrderByPriceUseCase implements UseCase<FilterOrderByPriceUseCaseInput, FilterOrderByUseCaseOutput> {

    private final OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public FilterOrderByUseCaseOutput handle(FilterOrderByPriceUseCaseInput input) {
        try {
            logger.info("Filtering orders by max and min price: " + input.getMaxPrice() + " " + input.getMinPrice());

            if (input.getMinPrice() < 500)
                throw new Exception("Min price can not be more than 500");

            return new FilterOrderByUseCaseOutput(
                    orderService.getAllByMaxAndMinPrice(input.getMaxPrice(), input.getMinPrice()),
                    null
            );
        } catch (Exception e) {
            logger.error("Error while filtering orders by min and max price "
                    + input.getMaxPrice() + " " + input.getMinPrice());
            e.printStackTrace();
            return new FilterOrderByUseCaseOutput(
                    null,
                    "Error while getting orders. Cause: " + e.getMessage()
            );
        }
    }
}
