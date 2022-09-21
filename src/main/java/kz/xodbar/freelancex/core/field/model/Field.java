package kz.xodbar.freelancex.core.field.model;

import java.util.List;
import kz.xodbar.freelancex.core.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Field {
    private Long id;
    private String name;
    private List<Order> orders;

    @Override
    public String toString() {
        return "id: " + id + " | name: " + name + " | ordersSize: " + orders.size();
    }
}
