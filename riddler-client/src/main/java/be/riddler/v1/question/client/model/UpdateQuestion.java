package be.riddler.v1.question.client.model;

import org.jspecify.annotations.NonNull;

/**
 * UpdateQuestion
 *
 * @author dnoulet
 * @version 1.0.0 03/05/2026
 */
public record UpdateQuestion(@NonNull String title, @NonNull String question, @NonNull QuestionType type) {
}
