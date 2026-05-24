package be.riddler.v1.invitation.client.model;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * UpdateInvitation
 *
 * @author dnoulet
 * @version 1.0.0 24/05/2026
 */
public record UpdateInvitation(@NonNull List<@NonNull UUID> questions) {
}
