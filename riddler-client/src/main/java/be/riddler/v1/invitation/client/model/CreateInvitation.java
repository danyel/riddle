package be.riddler.v1.invitation.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * CreateInvitation
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public record CreateInvitation(
        @NonNull @Valid @NotNull @JsonProperty("participant_id") UUID participantId,
        @NonNull @Valid @NotNull @JsonProperty("publication_id") UUID publicationId
) {
}
