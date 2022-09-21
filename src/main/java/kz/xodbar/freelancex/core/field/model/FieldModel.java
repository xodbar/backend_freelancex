package kz.xodbar.freelancex.core.field.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.order.model.OrderModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_fields")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderModel> orders;

    public Field toDto() {
        List<Order> ordersDto = new ArrayList<>();
        orders.forEach(orderModel -> ordersDto.add(orderModel.toDto()));

        return new Field(
                this.id,
                this.name,
                ordersDto
        );
    }
}
