package be.riddler.v1.question.domain;

import java.util.UUID;

/**
 * UpdateWithId
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record UpdateWithId(UUID id, UpdateQuestion question) {
}
