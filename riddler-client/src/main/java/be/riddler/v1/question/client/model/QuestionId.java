package be.riddler.v1.question.client.model;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * QuestionId
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public record QuestionId(UUID id) {
    public static @NonNull QuestionId fromQuestionId(@NonNull UUID uuid) {
        return new QuestionId(uuid);
    }
}
