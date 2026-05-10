package be.riddler.v1.participant.feature;

import be.riddler.v1.participant.client.domain.ParticipantDetail;
import be.riddler.v1.participant.client.domain.ParticipantId;
import org.jspecify.annotations.NonNull;

/**
 * FindByParticipantIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface FindByParticipantIdFeature {
    @NonNull ParticipantDetail findByParticipantId(@NonNull ParticipantId participantId);
}
