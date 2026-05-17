package be.riddler.v1.participant.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * ParticipantDetail
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record Participant(
        @NonNull UUID id,
        @JsonProperty("first_name")
        @NonNull String firstName,
        @JsonProperty("last_name")
        @NonNull String lastName,
        @JsonProperty("email_address")
        @NonNull String emailAddress,
        @JsonProperty("stored_token")
        String storedToken,
        String photo,
        String cv,
        @NonNull List<@NonNull Category> categories
) {
}
