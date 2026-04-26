package be.riddler.domain.ui.port;

import be.riddler.domain.ui.domain.Translation;

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
