package be.riddler.v1.participant.domain.feature;

import be.riddler.v1.common.domain.feature.DomainFeature;
import be.riddler.v1.participant.domain.ParticipantDetail;
import be.riddler.v1.participant.domain.ParticipantId;
import be.riddler.v1.participant.mapper.ParticipantMapper;
import be.riddler.v1.participant.repository.ParticipantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * FindParticipantByIdFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FindByParticipantIdFeature implements DomainFeature<ParticipantId, ParticipantDetail> {
    private final ParticipantRepository participantRepository;

    @Override
    public ParticipantDetail executeWithReturn(ParticipantId participantId) {
        var participant = participantRepository.findById(participantId.id());

        if (participant.isPresent()) {
            return ParticipantMapper.toParticipantDetail(participant.get());
        } else {
            throw new EntityNotFoundException("Participant not found");
        }
    }
}
