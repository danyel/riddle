package be.riddler.v1.participant.mapper;

import be.riddler.v1.participant.client.model.CreateParticipant;
import be.riddler.v1.participant.client.model.ParticipantDetail;
import be.riddler.v1.participant.entity.ParticipantEntity;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Base64;

/**
 * ParticipantMapper
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@UtilityClass
public class ParticipantMapper {
    public static @NonNull ParticipantEntity fromCreateParticipant(@NonNull CreateParticipant createParticipant) {
        return ParticipantEntity.builder()
                .email(createParticipant.emailAddress())
                .firstName(createParticipant.firstName())
                .lastName(createParticipant.lastName())
                .build();
    }

    public static @NonNull ParticipantDetail fromParticipantEntity(@NonNull ParticipantEntity participant) {
        String photo = null;
        String cv = null;
        if (participant.getPhoto() != null) {
            photo = Base64.getEncoder().encodeToString(participant.getPhoto());
        }
        if (participant.getCv() != null) {
            cv = Base64.getEncoder().encodeToString(participant.getCv());
        }

        return new ParticipantDetail(participant.getId(), participant.getFirstName(), participant.getLastName(), participant.getEmail(), participant.getStoredToken(), photo, cv);
    }
}
