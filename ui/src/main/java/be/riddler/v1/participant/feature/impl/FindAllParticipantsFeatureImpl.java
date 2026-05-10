package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.participant.client.model.ParticipantDetail;
import be.riddler.v1.participant.feature.FindAllParticipantsFeature;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * FindParticipantByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class FindAllParticipantsFeatureImpl implements FindAllParticipantsFeature {
    private final ParticipantRepository participantRepository;

    @Override
    public @NonNull List<@NonNull ParticipantDetail> findAll() {
        return participantRepository.findAll()
                .stream()
                .map(ParticipantMapper::fromParticipantEntity)
                .toList();
    }
}
