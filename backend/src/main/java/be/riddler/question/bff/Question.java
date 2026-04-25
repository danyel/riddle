package be.riddler.question.bff;

import be.riddler.question.domain.QuestionType;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Question
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record Question(@NonNull UUID id, @NonNull String question, @NonNull QuestionType type) {
}
