package be.riddler.v1.activity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

/**
 * Activity
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record ActivityDetail(
        UUID id,
        @JsonProperty("question_id")
        UUID questionId,
        @JsonProperty("action_type")
        String actionType,
        @JsonProperty("element_id")
        String elementId,
        Instant timestamp,
        @JsonProperty("additional_data")
        String additionalData
) {
}
