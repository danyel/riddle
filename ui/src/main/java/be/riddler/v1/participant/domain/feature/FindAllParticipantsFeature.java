package be.riddler.v1.participant.domain.feature;

import be.riddler.v1.common.domain.feature.DomainFeature;
import be.riddler.v1.participant.domain.ParticipantDetail;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
public class FindAllParticipantsFeature implements DomainFeature<Void, List<ParticipantDetail>> {
    private final ParticipantRepository participantRepository;

    @Override
    public List<ParticipantDetail> executeWithoutParameters() {
        return participantRepository.findAll()
                .stream()
                .map(ParticipantMapper::toParticipantDetail)
                .toList();
    }
}
