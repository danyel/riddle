package be.riddler.v1.question.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * UpdateOption
 *
 * @author dnoulet
 * @version 1.0.0 29/05/2026
 */
public record UpdateOption(@NonNull @JsonProperty(value = "option_id") UUID optionId, @NonNull String value) {
}
