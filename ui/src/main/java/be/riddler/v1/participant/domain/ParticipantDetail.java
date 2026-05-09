package be.riddler.v1.participant.domain;

import java.util.UUID;

/**
 * ParticipantDetail
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record ParticipantDetail(
        UUID id,
        String firstName,
        String lastName,
        String emailAddress,
        String storedToken
) {
}
