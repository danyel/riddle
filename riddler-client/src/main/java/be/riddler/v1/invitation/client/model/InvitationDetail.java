package be.riddler.v1.invitation.client.model;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * InvitationDetail
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public record InvitationDetail(@NonNull UUID id, @NonNull UUID participantId, @NonNull List<@NonNull UUID> questions) {
    public InvitationDetail(UUID id, UUID participantId) {
        this(id, participantId, new ArrayList<>());
    }
}
