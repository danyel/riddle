package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.client.model.CreateInvitation;
import be.riddler.v1.invitation.client.model.InvitationDetail;
import be.riddler.v1.invitation.feature.SaveInvitationFeature;
import be.riddler.v1.invitation.mapper.InvitationMapper;
import be.riddler.v1.invitation.repository.InvitationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

/**
 * FindInvitationByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SaveInvitationFeatureImpl implements SaveInvitationFeature {
    private final InvitationRepository invitationRepository;

    @Override
    public InvitationDetail save(@NonNull CreateInvitation createInvitation) {
        var invitationEntity = InvitationMapper.fromCreateInvitation(createInvitation);
        invitationRepository.save(invitationEntity);
        return InvitationMapper.fromInvitationEntity(invitationEntity);
    }
}
