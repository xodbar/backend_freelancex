package kz.xodbar.freelancex.core.proposal.service;

import kz.xodbar.freelancex.core.order.service.OrderService;
import kz.xodbar.freelancex.core.proposal.ProposalRepository;
import kz.xodbar.freelancex.core.proposal.model.Proposal;
import kz.xodbar.freelancex.core.proposal.model.ProposalModel;
import kz.xodbar.freelancex.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;

    private final UserService userService;

    private final OrderService orderService;

    @Override
    public Proposal getProposalById(Long id) {
        return proposalRepository.findById(id).orElseThrow().toDto();
    }

    @Override
    public List<Proposal> getAllByCandidate(Long candidateId) {
        return proposalRepository.findAllByCandidate(userService.getModelById(candidateId))
                .stream().map(ProposalModel::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Proposal> getAllByOrder(Long orderId) {
        return proposalRepository.findAllByOrder(orderService.getModelById(orderId))
                .stream().map(ProposalModel::toDto).collect(Collectors.toList());
    }

    @Override
    public ProposalModel getModelById(Long id) {
        return proposalRepository.findById(id).orElseThrow();
    }

    @Override
    public Proposal createProposal(Proposal proposal) {
        return proposalRepository.save(new ProposalModel(
                null,
                userService.getModelById(proposal.getCandidate().getId()),
                orderService.getModelById(proposal.getOrder().getId()),
                proposal.getProposedPrice(),
                Timestamp.valueOf(LocalDateTime.now())
        )).toDto();
    }
}
