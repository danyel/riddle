package be.riddler.v1.participant.feature;

import be.riddler.v1.participant.client.domain.CreateParticipant;
import be.riddler.v1.participant.client.domain.ParticipantDetail;
import org.jspecify.annotations.NonNull;

/**
 * CreateParticipantFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface CreateParticipantFeature {
    @NonNull ParticipantDetail create(@NonNull CreateParticipant createParticipant);
}
