package be.riddler.v1.publication.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * CreatePublication
 *
 * @author dnoulet
 * @version 1.0.0 15/05/2026
 */
public record CreatePublication(
        @NonNull String title,
        @NonNull String description,
        @NonNull String proposal,
        @NonNull @JsonProperty("position_id") UUID positionId,
        @NonNull @JsonProperty("level_id") UUID levelId
) {
}
