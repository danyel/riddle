package be.riddler.v1.participant.feature;

import be.riddler.v1.participant.domain.ParticipantDetail;
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
public class FindAllParticipantsFeature {
    private final ParticipantRepository participantRepository;

    public @NonNull List<@NonNull ParticipantDetail> findAll() {
        return participantRepository.findAll()
                .stream()
                .map(ParticipantMapper::fromParticipantEntity)
                .toList();
    }
}
