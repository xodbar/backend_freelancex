package kz.xodbar.freelancex.core.order.service;

import kz.xodbar.freelancex.core.field.model.FieldModel;
import kz.xodbar.freelancex.core.field.service.FieldService;
import kz.xodbar.freelancex.core.order.OrderRepository;
import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.order.model.OrderModel;
import kz.xodbar.freelancex.core.order.model.OrderStatus;
import kz.xodbar.freelancex.core.proposal.model.Proposal;
import kz.xodbar.freelancex.core.proposal.model.ProposalModel;
import kz.xodbar.freelancex.core.proposal.service.ProposalService;
import kz.xodbar.freelancex.core.user.model.UserModel;
import kz.xodbar.freelancex.core.user.service.UserService;
import kz.xodbar.freelancex.useCase.order.get.order.OrderByEnum;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private FieldService fieldService;

    private ProposalService proposalService;

    @Autowired
    public void setFieldService(@NonNull @Lazy FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @Autowired
    public void setProposalService(@NonNull @Lazy ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Order createOrder(Order order) {
        try {
            logger.info("Creating new order: " + order.toString());

            if (orderRepository.findByTitle(order.getTitle()) != null)
                throw new Exception("This order already exists!");

            UserModel client = userService.getModelByUsername(order.getClient().getUsername());
            FieldModel field = fieldService.getModelByName(order.getField());

            return orderRepository.save(new OrderModel(
                    null,
                    order.getTitle(),
                    order.getContent(),
                    client,
                    null,
                    true,
                    OrderStatus.ACTIVE,
                    Timestamp.valueOf(LocalDateTime.now()),
                    Timestamp.valueOf(order.getDeadline()),
                    order.getPrice(),
                    field,
                    new ArrayList<>()
            )).toDto();
        } catch (Exception e) {
            logger.error("Error while creating new order: " + order.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order getById(Long id) {
        try {
            logger.info("Getting order by id: " + id);
            Order model = orderRepository.findById(id).orElseThrow().toDto();
            model.setProposals(proposalService.getAllByOrder(model.getId()));
            return model;
        } catch (Exception e) {
            logger.error("Error while getting order by id: " + id);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> getAllByField(String fieldName) {
        try {
            logger.info("Getting order by field: " + fieldName);

            FieldModel fieldModel = fieldService.getModelByName(fieldName);
            if (fieldModel == null)
                throw new Exception("No such field by name: " + fieldName);

            return convertModelsListToDto(orderRepository.findAllByFieldOrderByCreatedAtDesc(fieldModel));
        } catch (Exception e) {
            logger.error("Error while getting orders by field: " + fieldName);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order getByTitle(String title) {
        try {
            logger.info("Getting order by title: " + title);
            Order result = orderRepository.findByTitle(title).toDto();
            result.setProposals(proposalService.getAllByOrder(result.getId()));
            return result;
        } catch (Exception e) {
            logger.error("Error while getting order by title: " + title);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order updateOrder(Order order) {
        try {
            logger.info("Updating order: " + order.toString());

            UserModel client = userService.getModelByUsername(order.getClient().getUsername());
            UserModel contractor = userService.getModelByUsername(order.getContractor().getUsername());
            FieldModel field = fieldService.getModelByName(order.getField());

            List<ProposalModel> proposalModels = new ArrayList<>();
            for (Proposal proposal : order.getProposals())
                proposalModels.add(proposalService.getModelById(proposal.getId()));

            OrderModel model = new OrderModel(
                    order.getId(),
                    order.getTitle(),
                    order.getContent(),
                    client,
                    contractor,
                    order.isActive(),
                    order.getStatus(),
                    Timestamp.valueOf(order.getCreatedAt()),
                    Timestamp.valueOf(order.getDeadline()),
                    order.getPrice(),
                    field,
                    proposalModels
            );

            Order result = orderRepository.save(model).toDto();
            result.setProposals(proposalService.getAllByOrder(result.getId()));
            return result;
        } catch (Exception e) {
            logger.error("Error while updating order: " + order.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        try {
            logger.info("Updating order status (id, status): " + orderId + ", " + status);

            OrderModel model = orderRepository.findById(orderId).orElseThrow();
            model.setStatus(status);

            return orderRepository.save(model).toDto();
        } catch (Exception e) {
            logger.error("Error while updating order status (id, status): " + orderId + ", " + status);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteOrder(Order order) {
        try {
            logger.warn("Deleting order: " + order.toString());
            orderRepository.deleteById(order.getId());
        } catch (Exception e) {
            logger.error("Error while deleting order: " + order.toString());
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAllByStatus(OrderStatus status) {
        try {
            logger.info("Getting all orders by status: " + status.name());
            return convertModelsListToDto(orderRepository.findAllByStatusOrderByCreatedAtDesc(status));
        } catch (Exception e) {
            logger.error("Error while getting all orders by status: " + status.name());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> getAllByMaxAndMinPrice(Integer max, Integer min) {
        try {
            logger.info("Getting all orders by filtering max " + max + " and min " + min + " price");
            List<OrderModel> model = orderRepository.findAllByStatusOrderByPriceDesc(OrderStatus.ACTIVE);
            List<Order> result = new ArrayList<>();

            for (OrderModel om : model)
                if (om.getPrice() >= min && om.getPrice() <= max)
                    result.add(om.toDto());

            return result;

        } catch (Exception e) {
            logger.error("Error while getting all orders by filtering max " + max + " and min " + min + " price");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> getAllByStatusAndOrder(OrderStatus status, OrderByEnum order) {
        try {
            logger.info("Getting all orders by status: " + status + " and ordering: " + order);

            List<OrderModel> orderModels;

            switch (order) {
                case ORDER_BY_PRICE_ASC -> orderModels = orderRepository.findAllByStatusOrderByPriceAsc(status);
                case ORDER_BY_PRICE_DESC -> orderModels = orderRepository.findAllByStatusOrderByPriceDesc(status);
                case ORDER_BY_DEADLINE_ASC -> orderModels = orderRepository.findAllByStatusOrderByDeadlineAsc(status);
                case ORDER_BY_DEADLINE_DESC -> orderModels = orderRepository.findAllByStatusOrderByDeadlineDesc(status);
                case ORDER_BY_CREATED_AT_ASC -> orderModels = orderRepository.findAllByStatusOrderByCreatedAtAsc(status);
                case ORDER_BY_CREATED_AT_DESC -> orderModels = orderRepository.findAllByStatusOrderByCreatedAtDesc(status);

                default -> orderModels = null;
            }

            return convertModelsListToDto(orderModels);
        } catch (Exception e) {
            logger.error("Error while getting all orders by status: " + status + " and ordering: " + order);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderModel getModelById(Long id) {
        try {
            logger.info("Getting model by id: " + id);
            return orderRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            logger.info("Error while getting model by id: " + id);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderModel getModelByTitle(String title) {
        try {
            logger.info("Getting model by title: " + title);
            return orderRepository.findByTitle(title);
        } catch (Exception e) {
            logger.error("Error while getting model by title: " + title);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Order> searchByTitle(String query) {
        try {
            logger.info("Searching orders by query: " + query);
            return convertModelsListToDto(orderRepository.findAllByTitleContainingIgnoreCase(query));
        } catch (Exception e) {
            logger.info("Failed to search orders by query: " + query);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String clean() {
        try {
            logger.warn("Deleting inactive orders");
            orderRepository.deleteAllByStatus(OrderStatus.FINISHED);
            return "SUCCESS";
        } catch (Exception e) {
            logger.error("Error while deleting inactive orders");
            e.printStackTrace();
            return "FAILED";
        }
    }

    @Override
    public boolean orderAlreadyExists(String title) {
        return orderRepository.findByTitle(title) != null;
    }

    private List<Order> convertModelsListToDto(List<OrderModel> orderModels) {
        List<Order> orders = new ArrayList<>();
        orderModels.forEach(orderModel -> orders.add(orderModel.toDto()));
        orders.forEach(order -> order.setProposals(proposalService.getAllByOrder(order.getId())));

        return orders;
    }
}
