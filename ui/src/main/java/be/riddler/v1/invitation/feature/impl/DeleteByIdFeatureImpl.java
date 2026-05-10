package be.riddler.v1.invitation.feature.impl;

import be.riddler.v1.invitation.feature.DeleteByIdFeature;
import be.riddler.v1.invitation.repository.InvitationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * FindInvitationByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteByIdFeatureImpl implements DeleteByIdFeature {
    private final InvitationRepository invitationRepository;

    @Override
    public void deleteById(@NonNull UUID invitationId) {
        invitationRepository.deleteById(invitationId);
    }
}
