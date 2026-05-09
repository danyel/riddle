package be.riddler.v1.participant.domain.feature;

import be.riddler.v1.participant.domain.CreateParticipant;
import be.riddler.v1.participant.domain.ParticipantDetail;
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
public class CreateParticipantFeature {
    private final ParticipantRepository participantRepository;

    public @NonNull ParticipantDetail create(@NonNull CreateParticipant createParticipant) {
        var participantEntity = ParticipantMapper.fromCreateParticipant(createParticipant);
        participantRepository.save(participantEntity);
        return ParticipantMapper.fromParticipantEntity(participantEntity);
    }
}
