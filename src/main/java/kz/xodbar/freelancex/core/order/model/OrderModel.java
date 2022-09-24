package kz.xodbar.freelancex.core.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import kz.xodbar.freelancex.core.field.model.FieldModel;
import kz.xodbar.freelancex.core.proposal.model.ProposalModel;
import kz.xodbar.freelancex.core.user.model.User;
import kz.xodbar.freelancex.core.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_orders")
@SecondaryTables({
        @SecondaryTable(
                name = "t_fields_orders",
                pkJoinColumns = {
                        @PrimaryKeyJoinColumn(
                                name = "orders_id",
                                referencedColumnName = "id"
                        )
                }
        )
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "content", nullable = false, unique = true)
    private String content;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private UserModel client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractor_id", referencedColumnName = "id")
    private UserModel contractor;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "deadline", nullable = false)
    private Timestamp deadline;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private FieldModel field;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ProposalModel> proposals;

    public Order toDto() {

        User contractor = (this.contractor == null ? null : this.contractor.toDto());

        return new Order(
                this.id,
                this.title,
                this.content,
                this.client.toDto(),
                contractor,
                this.isActive,
                this.status,
                this.createdAt.toLocalDateTime(),
                this.deadline.toLocalDateTime(),
                this.price,
                this.field.getName(),
                null
        );
    }

    @Override
    public String toString() {
        return "";
    }
}
