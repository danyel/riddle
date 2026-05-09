package be.riddler.v1.ui.domain.feature;

import be.riddler.v1.common.domain.feature.DomainFeature;
import be.riddler.v1.ui.domain.Translation;
import be.riddler.v1.ui.domain.TranslationProperty;
import be.riddler.v1.ui.repository.KeyRepository;
import be.riddler.v1.ui.repository.TranslationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * GetTranslationFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetTranslationFeature implements DomainFeature<TranslationProperty, Translation> {
    private final TranslationRepository translationRepository;
    private final KeyRepository keyRepository;

    @Override
    public Translation executeWithReturn(TranslationProperty translationProperty) {
        var keyEntity = keyRepository.findByKey(translationProperty.key());
        return translationRepository.findByLanguageAndKey_Id(translationProperty.lang(), keyEntity.getId())
                .map(e -> new Translation(e.getKey().getKey(), e.getValue()))
                .orElse(new Translation(translationProperty.key(), "UNKNOWN"));
    }
}
