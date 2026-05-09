package be.riddler.v1.participant.domain.feature;

import be.riddler.v1.participant.domain.ParticipantDetail;
import be.riddler.v1.participant.domain.ParticipantId;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * FindParticipantByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FindByParticipantIdFeature {
    private final ParticipantRepository participantRepository;

    public @NonNull ParticipantDetail findByParticipantId(@NonNull ParticipantId participantId) {
        var participant = participantRepository.findById(participantId.id());

        if (participant.isPresent()) {
            return ParticipantMapper.fromParticipantEntity(participant.get());
        } else {
            throw new EntityNotFoundException("Participant not found");
        }
    }
}
