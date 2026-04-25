package be.riddler.answer.bff;

import java.util.UUID;

/**
 * Answer
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record Answer(UUID id, String value, UUID questionId) {
}
