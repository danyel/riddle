package be.riddler.v1.participant.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ParticipantDetail
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record CreateParticipant(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("email_address")
        String emailAddress
) {
}
