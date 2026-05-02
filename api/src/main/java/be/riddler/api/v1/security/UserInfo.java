package be.riddler.api.v1.security;

import java.util.List;

/**
 * UserInfo
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public record UserInfo(String name, List<String> roles) {
}
