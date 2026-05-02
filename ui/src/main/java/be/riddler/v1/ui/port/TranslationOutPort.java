package be.riddler.v1.ui.port;

import be.riddler.v1.ui.domain.Translation;

import java.util.List;

/**
 * TranslationOutPort
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
public interface TranslationOutPort {
    List<Translation> getTranslations(String language);
}
