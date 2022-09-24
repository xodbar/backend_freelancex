package kz.xodbar.freelancex.core.order.model;

import kz.xodbar.freelancex.core.proposal.model.Proposal;
import kz.xodbar.freelancex.core.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    private Long id;
    private String title;
    private String content;
    private User client;
    private User contractor;
    private boolean isActive;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private Integer price;
    private String field;
    private List<Proposal> proposals;

    @Override
    public String toString() {
        return "id: " + id + " | title: " + title + " | content: " + content + " | client: " + client.toString() +
                " | isActive: " + isActive + " | status: "
                + status.toString() + " | createdAt: " + createdAt + " | deadline: " + deadline + " | field: "
                + field.toString();
    }
}
