package be.riddler.v1.invitation.mapper;

import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.InvitationDetail;
import be.riddler.v1.invitation.entity.InvitationEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

/**
 * InvitationMapper
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@UtilityClass
public class InvitationMapper {
    public static InvitationEntity fromCreateInvitation(CreateInvitation createInvitation) {
        return InvitationEntity.builder().participantId(createInvitation.participantId()).build();
    }

    public static InvitationDetail fromInvitationEntity(InvitationEntity invitationEntity) {
        List<java.util.UUID> safeQuestions = Optional.ofNullable(invitationEntity.getQuestions())
                .orElseGet(List::of);
        return safeQuestions.isEmpty() ? new InvitationDetail(invitationEntity.getId(), invitationEntity.getParticipantId()) : new InvitationDetail(invitationEntity.getId(), invitationEntity.getParticipantId(), safeQuestions);
    }
}
