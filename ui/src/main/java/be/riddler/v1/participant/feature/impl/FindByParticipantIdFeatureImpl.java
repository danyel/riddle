package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.participant.client.model.ParticipantDetail;
import be.riddler.v1.participant.client.model.ParticipantId;
import be.riddler.v1.participant.feature.FindByParticipantIdFeature;
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
class FindByParticipantIdFeatureImpl implements FindByParticipantIdFeature {
    private final ParticipantRepository participantRepository;

    @Override
    public @NonNull ParticipantDetail findByParticipantId(@NonNull ParticipantId participantId) {
        var participant = participantRepository.findById(participantId.id());

        if (participant.isPresent()) {
            return ParticipantMapper.fromParticipantEntity(participant.get());
        } else {
            throw new EntityNotFoundException("Participant not found");
        }
    }
}
