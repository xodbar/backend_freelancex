package kz.xodbar.freelancex.core.proposal;

import kz.xodbar.freelancex.core.order.model.OrderModel;
import kz.xodbar.freelancex.core.proposal.model.ProposalModel;
import kz.xodbar.freelancex.core.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<ProposalModel, Long> {
    List<ProposalModel> findAllByCandidate(UserModel userModel);
    List<ProposalModel> findAllByOrder(OrderModel orderModel);
}
