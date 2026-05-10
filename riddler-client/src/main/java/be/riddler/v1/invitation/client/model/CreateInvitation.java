package be.riddler.v1.invitation.client.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * CreateInvitation
 *
 * @author dnoulet
 * @version 1.0.0 10/05/2026
 */
public record CreateInvitation(@NonNull @Valid @NotNull UUID participantId) {
}
