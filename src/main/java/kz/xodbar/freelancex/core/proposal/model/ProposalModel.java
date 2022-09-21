package kz.xodbar.freelancex.core.proposal.model;

import kz.xodbar.freelancex.core.order.model.OrderModel;
import kz.xodbar.freelancex.core.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "t_proposals")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProposalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id", nullable = false)
    private UserModel candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private OrderModel order;

    @Column(nullable = false)
    private Integer proposedPrice;

    @Column(nullable = false)
    private Timestamp createdAt;

    public Proposal toDto() {
        return new Proposal(
                this.getId(),
                this.getCandidate().toDto(),
                this.getOrder().toDto(),
                this.proposedPrice,
                this.createdAt.toLocalDateTime()
        );
    }
}
