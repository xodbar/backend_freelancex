package kz.xodbar.freelancex.useCase.order.get.filter.byField;

import kz.xodbar.freelancex.core.field.service.FieldService;
import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.useCase.UseCase;
import kz.xodbar.freelancex.useCase.order.get.filter.FilterOrderByUseCaseOutput;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterOrderByFieldUseCase implements UseCase<FilterOrderByFieldUseCaseInput, FilterOrderByUseCaseOutput> {

    private final OrderService orderService;

    private final FieldService fieldService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public FilterOrderByUseCaseOutput handle(FilterOrderByFieldUseCaseInput input) {
        try {
            logger.info("Filtering orders by field: " + input.getFieldName());

            if (fieldService.getByName(input.getFieldName()) == null)
                throw new Exception("No such field!");

            return new FilterOrderByUseCaseOutput(
                    orderService.getAllByField(input.getFieldName()),
                    null
            );
        } catch (Exception e) {
            logger.error("Error while filtering orders by field " + input.getFieldName());
            e.printStackTrace();
            return new FilterOrderByUseCaseOutput(
                    null,
                    "Error while getting orders. Cause: " + e.getMessage()
            );
        }
    }
}
