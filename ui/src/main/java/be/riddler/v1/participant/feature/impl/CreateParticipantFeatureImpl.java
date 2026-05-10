package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.participant.client.domain.CreateParticipant;
import be.riddler.v1.participant.client.domain.ParticipantDetail;
import be.riddler.v1.participant.feature.CreateParticipantFeature;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

/**
 * CreateParticipantFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CreateParticipantFeatureImpl implements CreateParticipantFeature {
    private final ParticipantRepository participantRepository;

    public @NonNull ParticipantDetail create(@NonNull CreateParticipant createParticipant) {
        var participantEntity = ParticipantMapper.fromCreateParticipant(createParticipant);
        participantRepository.save(participantEntity);
        return ParticipantMapper.fromParticipantEntity(participantEntity);
    }
}
