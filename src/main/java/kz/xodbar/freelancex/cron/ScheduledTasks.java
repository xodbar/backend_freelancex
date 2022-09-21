package kz.xodbar.freelancex.cron;

import kz.xodbar.freelancex.core.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final OrderService orderService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 0 0 1 * ?")
    public void deleteInactiveOrders() {
        logger.warn("Scheduled deleting of inactive orders started");
        String result = orderService.clean();
        logger.warn("Deleting inactive orders result: " + result);
    }
}
