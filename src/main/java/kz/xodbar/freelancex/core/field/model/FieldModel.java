package kz.xodbar.freelancex.core.field.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.order.model.OrderModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_fields")
@SecondaryTables({
        @SecondaryTable(
                name = "t_fields_orders",
                pkJoinColumns = {
                        @PrimaryKeyJoinColumn(
                                name = "field_model_id",
                                referencedColumnName = "id"
                        )
                }
        )
})
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderModel> orders;

    public Field toDto() {
        List<Order> ordersDto = new ArrayList<>();

        List<OrderModel> orderss = orders;

        System.out.println("orders = " + orders.size());

        if (orders != null && orders.size() > 0)
            orders.forEach(orderModel -> ordersDto.add(orderModel.toDto()));

        return new Field(
                this.id,
                this.name,
                ordersDto
        );
    }

    @Override
    public String toString() {
        return "";
    }
}
