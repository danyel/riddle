package be.riddler.v1.participant.client.model;

import java.util.UUID;

/**
 * FindById
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record ParticipantId(UUID id) {
    public static ParticipantId from(UUID participantId) {
        return new ParticipantId(participantId);
    }
}
