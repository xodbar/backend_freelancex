package kz.xodbar.freelancex.core.order.service;

import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.order.model.OrderModel;
import kz.xodbar.freelancex.core.order.model.OrderStatus;

import java.util.List;
import kz.xodbar.freelancex.useCase.order.get.order.OrderByEnum;

public interface OrderService {
    Order createOrder(Order order);

    Order getById(Long id);

    List<Order> getAllByField(String fieldName);

    Order getByTitle(String title);

    boolean orderAlreadyExists(String title);

    Order updateOrder(Order order);

    Order updateOrderStatus(Long orderId, OrderStatus status);

    List<Order> getAllByStatus(OrderStatus status);

    List<Order> getAllByStatusAndOrder(OrderStatus status, OrderByEnum order);

    List<Order> getAllByMaxAndMinPrice(Integer max, Integer min);

    void deleteOrder(Order order);

    OrderModel getModelById(Long id);

    OrderModel getModelByTitle(String title);

    List<Order> searchByTitle(String query);

    String clean();
}
