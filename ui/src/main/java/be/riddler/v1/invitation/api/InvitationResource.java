package be.riddler.v1.invitation.api;

import be.riddler.v1.invitation.client.InvitationClient;
import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.InvitationDetail;
import be.riddler.v1.invitation.feature.DeleteByIdFeature;
import be.riddler.v1.invitation.feature.FindAllByParticipantIdFeature;
import be.riddler.v1.invitation.feature.FindByIdFeature;
import be.riddler.v1.invitation.feature.SaveInvitationFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * InvitationResource
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@RestController
@RequestMapping("/v1/invitations")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class InvitationResource implements InvitationClient {
    private final SaveInvitationFeature saveInvitationFeature;
    private final FindByIdFeature findByIdFeature;
    private final DeleteByIdFeature deleteByIdFeature;
    private final FindAllByParticipantIdFeature findAllByParticipantIdFeature;

    @Override
    public @NonNull InvitationDetail create(@NonNull CreateInvitation createInvitation) {
        return saveInvitationFeature.save(createInvitation);
    }

    @Override
    public @NonNull InvitationDetail findById(@NonNull UUID invitationId) {
        return findByIdFeature.findById(invitationId);
    }

    @Override
    public void deleteById(@NonNull UUID invitationId) {
        deleteByIdFeature.deleteById(invitationId);
    }

    @Override
    public @NonNull List<@NonNull InvitationDetail> findInvitationsByParticipantId(@NonNull UUID participantId) {
        return findAllByParticipantIdFeature.findAllByParticipantId(participantId);
    }
}
