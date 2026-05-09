package be.riddler.v1.ui.api;

import be.riddler.v1.question.domain.QuestionType;
import be.riddler.v1.ui.domain.SupportedLanguages;
import be.riddler.v1.ui.domain.Translation;
import be.riddler.v1.ui.domain.TranslationProperty;
import be.riddler.v1.ui.feature.GetIconsFeature;
import be.riddler.v1.ui.feature.GetTranslationFeature;
import be.riddler.v1.ui.feature.GetTranslationsByLanguageFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

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
    private final GetIconsFeature getIconsFeature;
    private final GetTranslationsByLanguageFeature getTranslationsByLanguageFeature;
    private final GetTranslationFeature getTranslationFeature;

    @Override
    public List<String> icons() {
        return getIconsFeature.findAll();
    }

    @Override
    public List<String> supportedLanguages() {
        return SupportedLanguages.languages();
    }

    @Override
    public List<String> questionTypes() {
        return Stream.of(QuestionType.values()).map(Enum::name).toList();
    }

    @Override
    public List<Translation> translations(String language) {
        return getTranslationsByLanguageFeature.findAll(language);
    }

    @Override
    public Translation translate(String language, String key) {
        return getTranslationFeature.translateForProperties(new TranslationProperty(language, key));
    }
}
