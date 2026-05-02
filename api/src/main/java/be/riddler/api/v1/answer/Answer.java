package be.riddler.api.v1.answer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Answer
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record Answer(UUID id, String value, @JsonProperty(value = "question_id") UUID questionId) {
}
