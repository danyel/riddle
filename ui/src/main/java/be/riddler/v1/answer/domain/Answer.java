package be.riddler.v1.answer.domain;

import java.util.UUID;

/**
 * Answer
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record Answer(UUID id,
                     String value,
                     UUID questionId
) {
    public Answer(String value, UUID questionId) {
        this(null, value, questionId);
    }
}
