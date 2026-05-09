package be.riddler.v1.activity.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Activity
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record Activity(
        UUID id,
        UUID questionId,
        String actionType,
        String elementId,
        Instant timestamp,
        String additionalData
) {
    Activity(UUID questionId, String actionType, String elementId, Instant timestamp, String additionalData) {
        this(null, questionId, actionType, elementId, timestamp, additionalData);
    }
}
