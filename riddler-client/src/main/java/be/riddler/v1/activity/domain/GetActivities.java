package be.riddler.v1.activity.domain;

import java.util.UUID;

/**
 * GetActivities
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record GetActivities(UUID questionId, String username) {
}
