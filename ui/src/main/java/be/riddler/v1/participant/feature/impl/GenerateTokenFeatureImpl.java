package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.participant.client.domain.ParticipantId;
import be.riddler.v1.participant.codec.TokenProvider;
import be.riddler.v1.participant.feature.GenerateTokenFeature;
import be.riddler.v1.participant.repository.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * GenerateTokenFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GenerateTokenFeatureImpl implements GenerateTokenFeature {
    private final ParticipantRepository participantRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public void generate(@NonNull ParticipantId participantId) {
        participantRepository.findById(participantId.id())
                .ifPresentOrElse(participant -> {
                    var generateToken = tokenProvider.generateToken(participantId.id());
                    participant.setStoredToken(generateToken);
                    participantRepository.save(participant);
                }, () -> {
                    throw new EntityNotFoundException("Participant not found with id: %s".formatted(participantId));
                });
    }
}
