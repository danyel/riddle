package be.riddler.v1.ui.port;

import be.riddler.v1.ui.domain.Translation;

/**
 * TranslationOutPort
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface TranslateOutPort {
    Translation translate(String language, String key);
}
