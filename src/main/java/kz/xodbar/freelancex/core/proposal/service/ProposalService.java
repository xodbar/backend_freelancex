package kz.xodbar.freelancex.core.proposal.service;

import kz.xodbar.freelancex.core.proposal.model.Proposal;
import kz.xodbar.freelancex.core.proposal.model.ProposalModel;

import java.util.List;

public interface ProposalService {
    Proposal getProposalById(Long id);

    List<Proposal> getAllByCandidate(Long candidateId);

    List<Proposal> getAllByOrder(Long orderId);

    ProposalModel getModelById(Long id);

    Proposal createProposal(Proposal proposal);
}
