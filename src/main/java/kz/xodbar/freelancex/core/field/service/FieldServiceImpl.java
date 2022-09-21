package kz.xodbar.freelancex.core.field.service;

import kz.xodbar.freelancex.core.field.FieldRepository;
import kz.xodbar.freelancex.core.field.model.Field;
import kz.xodbar.freelancex.core.field.model.FieldModel;
import kz.xodbar.freelancex.core.order.model.OrderModel;
import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.core.order.service.OrderServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;

    private OrderService orderService;

    @Autowired
    public void setOrderService(@NonNull OrderService orderService) {
        this.orderService = orderService;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Field createField(Field field) {
        try {
            logger.info("Adding new field: " + field.toString());

            if (getByName(field.getName()) != null)
                throw new Exception("Field " + field.getName() + " already exists");

            fieldRepository.save(new FieldModel(
                    field.getId(),
                    field.getName(),
                    new ArrayList<>()
            ));

            return fieldRepository.findByName(field.getName()).toDto();
        } catch (Exception e) {
            logger.error("Error while creating new field: " + field.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Field getById(Long id) {
        try {
            logger.info("Getting field by id: " + id);
            return fieldRepository.findById(id).orElseThrow().toDto();
        } catch (Exception e) {
            logger.error("Error while getting field by id: " + id);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Field getByName(String name) {
        try {
            logger.info("Getting field by name: " + name);
            return fieldRepository.findByName(name).toDto();
        } catch (Exception e) {
            logger.error("Error while getting field by name: " + name);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Field updateField(Field field) {
        try {
            logger.info("Updating field: " + field.toString());

            List<OrderModel> orderModels = new ArrayList<>();
            field.getOrders().forEach(orderDto -> orderModels.add(orderService.getModelById(orderDto.getId())));

            return fieldRepository.save(new FieldModel(
                    field.getId(),
                    field.getName(),
                    orderModels
            )).toDto();
        } catch (Exception e) {
            logger.error("Error while updating field: " + field.toString());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteField(Field field) {
        try {
            logger.info("Deleting field: " + field.toString());
            fieldRepository.deleteById(field.getId());
        } catch (Exception e) {
            logger.error("Error while deleting field: " + field.toString());
            e.printStackTrace();
        }
    }

    @Override
    public FieldModel getModelById(Long id) {
        try {
            logger.info("Getting field model by id: " + id);
            return fieldRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            logger.error("Error while getting field model by id: " + id);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FieldModel getModelByName(String name) {
        try {
            logger.info("Getting field model by name: " + name);
            return fieldRepository.findByName(name);
        } catch (Exception e) {
            logger.error("Error while getting field model by name: " + name);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean fieldAlreadyExists(String fieldName) {
        return fieldRepository.findByName(fieldName) != null;
    }

    @Override
    public List<Field> getAllFields() {
        return fieldRepository.findAll().stream().map(FieldModel::toDto).collect(Collectors.toList());
    }
}
