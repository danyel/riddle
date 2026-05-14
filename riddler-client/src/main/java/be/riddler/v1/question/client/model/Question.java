package be.riddler.v1.question.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

import static be.riddler.v1.question.client.model.QuestionType.MULTIPLE_CHOICE;
import static be.riddler.v1.question.client.model.QuestionType.OPEN;
import static be.riddler.v1.question.client.model.QuestionType.REVIEW;
import static be.riddler.v1.question.client.model.QuestionType.SINGLE_CHOICE;

/**
 * Question
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record Question(
        UUID id,
        @NonNull String title,
        @NonNull String question,
        @NonNull QuestionType type,
        @JsonProperty(value = "multiple_choice")
        boolean multipleChoice,
        @JsonProperty(value = "single_choice")
        boolean singleChoice,
        boolean open,
        boolean review
) {
    public Question(@NonNull UUID id, @NonNull String title, @NonNull String question, @NonNull QuestionType type) {
        this(id, title, question, type, MULTIPLE_CHOICE.equals(type), SINGLE_CHOICE.equals(type), OPEN.equals(type), REVIEW.equals(type));
    }

    public Question(@NonNull String title, @NonNull String question, @NonNull QuestionType type) {
        this(null, title, question, type, MULTIPLE_CHOICE.equals(type), SINGLE_CHOICE.equals(type), OPEN.equals(type), REVIEW.equals(type));
    }
}
