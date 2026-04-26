package be.riddler.domain.question.api;

import be.riddler.domain.question.domain.QuestionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

import static be.riddler.domain.question.domain.QuestionType.MULTIPLE_CHOICE;
import static be.riddler.domain.question.domain.QuestionType.OPEN;
import static be.riddler.domain.question.domain.QuestionType.SINGLE_CHOICE;

/**
 * Question
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record Question(
        @NonNull UUID id,
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
}
