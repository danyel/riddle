package be.riddler.v1.answer.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Answer
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record Answer(
        @NonNull @Valid UUID id,
        @NonNull @Valid String value,
        @NonNull @Valid @JsonProperty(value = "question_id") UUID questionId
) {
    public Answer(String value, UUID questionId) {
        this(null, value, questionId);
    }
}
