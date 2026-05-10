package be.riddler.v1.invitation.feature;

import be.riddler.v1.invitation.client.model.InvitationDetail;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * FindByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface FindByIdFeature {
    @NonNull InvitationDetail findById(@NonNull UUID invitationId);
}
