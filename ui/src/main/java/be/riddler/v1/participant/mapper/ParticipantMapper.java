package be.riddler.v1.participant.mapper;

import be.riddler.v1.participant.domain.CreateParticipant;
import be.riddler.v1.participant.domain.ParticipantDetail;
import be.riddler.v1.participant.entity.ParticipantEntity;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

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
                .createdBy(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName())
                .updatedBy(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName())
                .build();
    }

    public static @NonNull ParticipantDetail fromParticipantEntity(@NonNull ParticipantEntity participant) {
        return new ParticipantDetail(participant.getId(), participant.getFirstName(), participant.getLastName(), participant.getEmail(), participant.getStoredToken());
    }
}
