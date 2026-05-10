package be.riddler.v1.participant.feature.impl;

import be.riddler.v1.participant.feature.UploadCvFeature;
import be.riddler.v1.participant.repository.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * UploadCvFeatureImpl
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UploadCvFeatureImpl implements UploadCvFeature {
    private final ParticipantRepository participantRepository;

    @Transactional
    @Override
    public void uploadCv(UUID participantId, byte[] data) {
        participantRepository.findById(participantId)
                .ifPresentOrElse(participant -> {
                    participant.setCv(data);
                    participantRepository.save(participant);
                }, () -> {
                    throw new EntityNotFoundException("Could not find participant with id " + participantId);
                });
    }
}
