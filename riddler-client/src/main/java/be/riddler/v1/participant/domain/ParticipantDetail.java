package be.riddler.v1.participant.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * ParticipantDetail
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record ParticipantDetail(
        UUID id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("email_address")
        String emailAddress,
        @JsonProperty("stored_token")
        String storedToken
) {
}
