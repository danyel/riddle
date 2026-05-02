package be.riddler.v1.ui.domain;

import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * SupportedLanguages
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@UtilityClass
public final class SupportedLanguages {
    public static List<String> languages() {
        return List.of("en", "fr", "nl");
    }
}
