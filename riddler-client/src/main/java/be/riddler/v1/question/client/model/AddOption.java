package be.riddler.v1.question.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * AddOption
 *
 * @author dnoulet
 * @version 1.0.0 26/05/2026
 */
public record AddOption(@NonNull @JsonProperty(value = "question_id") UUID questionId, @NonNull String value) {
}
