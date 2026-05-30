package be.riddler.v1.common.model;

import org.jspecify.annotations.NonNull;

import java.util.UUID;

/**
 * Id
 *
 * @author dnoulet
 * @version 1.0.0 29/05/2026
 */
public record Id(@NonNull UUID id) {
}
