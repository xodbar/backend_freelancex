package kz.xodbar.freelancex.core.order;

import java.util.List;
import kz.xodbar.freelancex.core.field.model.FieldModel;
import kz.xodbar.freelancex.core.order.model.OrderModel;
import kz.xodbar.freelancex.core.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    OrderModel findByTitle(String title);

    @Query(value = "SELECT * FROM develop.t_orders WHERE price >= ?1 AND price <= ?2", nativeQuery = true)
    List<OrderModel> findAllByPriceFilter(Integer priceLessThan, Integer priceGreaterThan);

    List<OrderModel> findAllByStatusOrderByPriceAsc(OrderStatus status);

    List<OrderModel> findAllByStatusOrderByPriceDesc(OrderStatus status);

    List<OrderModel> findAllByStatusOrderByDeadlineAsc(OrderStatus status);

    List<OrderModel> findAllByStatusOrderByDeadlineDesc(OrderStatus status);

    List<OrderModel> findAllByStatusOrderByCreatedAtAsc(OrderStatus status);

    List<OrderModel> findAllByStatusOrderByCreatedAtDesc(OrderStatus status);

    List<OrderModel> findAllByFieldOrderByCreatedAtDesc(FieldModel fieldModel);

    void deleteAllByStatus(OrderStatus status);
}
