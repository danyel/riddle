package be.riddler.v1.question.domain;

import org.jspecify.annotations.NonNull;

/**
 * CreateQuestion
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public record CreateQuestion(@NonNull String question, @NonNull QuestionType type) {
}
