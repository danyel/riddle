package be.riddler.v1.question.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

import static be.riddler.v1.question.client.model.QuestionType.MULTIPLE_CHOICE;
import static be.riddler.v1.question.client.model.QuestionType.OPEN;
import static be.riddler.v1.question.client.model.QuestionType.SINGLE_CHOICE;

/**
 * Question
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record Question(
        UUID id,
        @NonNull String question,
        @NonNull QuestionType type,
        @JsonProperty(value = "multiple_choice")
        boolean multipleChoice,
        @JsonProperty(value = "single_choice")
        boolean singleChoice,
        boolean open
) {
    public Question(@NonNull UUID id, @NonNull String question, @NonNull QuestionType type) {
        this(id, question, type, MULTIPLE_CHOICE.equals(type), SINGLE_CHOICE.equals(type), OPEN.equals(type));
    }

    public Question(@NonNull String question, @NonNull QuestionType type) {
        this(null, question, type, MULTIPLE_CHOICE.equals(type), SINGLE_CHOICE.equals(type), OPEN.equals(type));
    }
}
