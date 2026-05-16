package be.riddler.v1.invitation.client.model;

import be.riddler.v1.publication.client.model.Publication;
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
public record Invitation(@NonNull UUID id, @NonNull Publication publication, @NonNull UUID participantId,
                         @NonNull List<@NonNull UUID> questions) {
    public Invitation(UUID id, UUID participantId, @NonNull Publication publication) {
        this(id, publication, participantId, new ArrayList<>());
    }
}
