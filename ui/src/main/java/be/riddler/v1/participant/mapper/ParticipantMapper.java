package be.riddler.v1.participant.mapper;

import be.riddler.v1.participant.domain.ParticipantDetail;
import be.riddler.v1.participant.entity.ParticipantEntity;
import lombok.experimental.UtilityClass;

/**
 * ParticipantMapper
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@UtilityClass
public class ParticipantMapper {
    public static ParticipantDetail toParticipantDetail(ParticipantEntity participant) {
        return new ParticipantDetail(participant.getId(), participant.getFirstName(), participant.getLastName(), participant.getEmail(), participant.getStoredToken());
    }
}
