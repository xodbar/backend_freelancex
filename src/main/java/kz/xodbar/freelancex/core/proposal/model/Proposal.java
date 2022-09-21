package kz.xodbar.freelancex.core.proposal.model;

import kz.xodbar.freelancex.core.order.model.Order;
import kz.xodbar.freelancex.core.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Proposal {
    private Long id;
    private User candidate;
    private Order order;
    private Integer proposedPrice;
    private LocalDateTime createdAt;
}
