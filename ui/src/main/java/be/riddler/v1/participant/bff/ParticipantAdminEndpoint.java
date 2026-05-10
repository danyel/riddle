package be.riddler.v1.participant.bff;

import be.riddler.v1.participant.client.ParticipantClient;
import be.riddler.v1.participant.client.domain.CreateParticipant;
import be.riddler.v1.participant.client.domain.ParticipantDetail;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * ParticipantEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@BrowserCallable
@RolesAllowed("ADMIN")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ParticipantAdminEndpoint {
    private final ParticipantClient participantClient;

    public @NonNull List<@NonNull ParticipantDetail> findAll() {
        return participantClient.findAll();
    }

    public @NonNull ParticipantDetail findById(@NonNull UUID id) {
        return participantClient.findById(id);
    }

    public void generateToken(UUID participantId) {
        participantClient.generateToken(participantId);
    }

    public @NonNull ParticipantDetail create(CreateParticipant createParticipant) {
        return participantClient.create(createParticipant);
    }
}
