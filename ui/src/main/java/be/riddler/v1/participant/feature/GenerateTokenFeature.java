package be.riddler.v1.participant.feature;

import be.riddler.v1.participant.client.domain.ParticipantId;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

/**
 * GenerateTokenFeature
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public interface GenerateTokenFeature {
    @Transactional
    void generate(@NonNull ParticipantId participantId);
}
