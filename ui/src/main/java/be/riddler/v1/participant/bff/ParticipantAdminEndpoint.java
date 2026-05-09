package be.riddler.v1.participant.bff;

import be.riddler.v1.participant.api.ParticipantApi;
import be.riddler.v1.participant.domain.CreateParticipant;
import be.riddler.v1.participant.domain.ParticipantDetail;
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
    private final ParticipantApi participantApi;

    public @NonNull List<@NonNull ParticipantDetail> findAll() {
        return participantApi.findAll();
    }

    public @NonNull ParticipantDetail findById(@NonNull UUID id) {
        return participantApi.findById(id);
    }

    public void generateToken(UUID participantId) {
        participantApi.generateToken(participantId);
    }

    public @NonNull ParticipantDetail create(CreateParticipant createParticipant) {
        return participantApi.create(createParticipant);
    }
}
