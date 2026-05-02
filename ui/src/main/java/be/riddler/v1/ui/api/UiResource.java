package be.riddler.v1.ui.api;

import be.riddler.v1.ui.domain.SupportedLanguages;
import be.riddler.v1.ui.port.IconNamesOutPort;
import be.riddler.v1.ui.port.TranslationOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ComponentsResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@RequestMapping(path = "/v1/ui")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class UiResource implements UiApi {
    private final TranslationOutPort translationOutPort;
    private final IconNamesOutPort iconNamesOutPort;

    @Override
    public List<String> icons() {
        return iconNamesOutPort.iconNames();
    }

    @Override
    public List<String> supportedLanguages() {
        return SupportedLanguages.languages();
    }

    @Override
    public List<Translation> translations(String language) {
        return translationOutPort.getTranslations(language)
                .stream()
                .map(e -> new Translation(e.key(), e.value()))
                .toList();
    }
}
