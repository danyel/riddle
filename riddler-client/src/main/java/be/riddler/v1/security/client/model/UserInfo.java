package be.riddler.v1.security.client.model;

import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * UserInfo
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public record UserInfo(@NonNull String name, @NonNull List<@NonNull String> roles) {
}
