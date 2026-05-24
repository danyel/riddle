package be.riddler.v1.category.feature.impl;

import be.riddler.v1.invitation.client.model.Invitation;
import be.riddler.v1.invitation.client.model.UpdateInvitation;
import be.riddler.v1.invitation.entity.InvitationQuestionEntity;
import be.riddler.v1.invitation.entity.InvitationQuestionId;
import be.riddler.v1.invitation.feature.UpdateInvitationFeature;
import be.riddler.v1.invitation.mapper.InvitationMapper;
import be.riddler.v1.invitation.repository.InvitationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * UpdateInvitationFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 24/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UpdateInvitationFeatureImpl implements UpdateInvitationFeature {
    private final InvitationRepository invitationRepository;

    @Transactional
    @Override
    public Invitation updateInvitation(UUID invitationId, UpdateInvitation updateInvitation) {
        var invitationEntity = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation with id " + invitationId + " not found"));
        var collect = invitationEntity.getQuestions()
                .stream()
                .collect(Collectors.toMap(invitationQuestionEntity -> invitationQuestionEntity.getId().questionId(), (q) -> q));
        var questions = invitationEntity.getQuestions()
                .stream()
                .filter(question -> !updateInvitation.questions().contains(question.getId().questionId()))
                .toList();
        invitationEntity.getQuestions().removeAll(questions);
        updateInvitation.questions()
                .forEach(e -> {
                    if (!collect.containsKey(e)) {
                        invitationEntity.getQuestions()
                                .add(InvitationQuestionEntity.builder()
                                        .id(new InvitationQuestionId(invitationId, e))
                                        .invitation(invitationEntity)
                                        .build()
                                );
                    }
                });
        return InvitationMapper.fromInvitationEntity(invitationRepository.save(invitationEntity));
    }
}
