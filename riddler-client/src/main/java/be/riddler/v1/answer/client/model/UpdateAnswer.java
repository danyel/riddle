package be.riddler.v1.answer.client.model;

import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * Answer
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
public record UpdateAnswer(
        @Valid @NonNull List<UpdateSolution> values
) {
}
