package be.riddler.v1.ui.domain.feature;

import be.riddler.v1.common.domain.feature.DomainFeature;
import be.riddler.v1.ui.domain.Translation;
import be.riddler.v1.ui.entity.KeyEntity;
import be.riddler.v1.ui.repository.KeyRepository;
import be.riddler.v1.ui.repository.TranslationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * GetTranslationFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetTranslationsByLanguageFeature implements DomainFeature<String, List<Translation>> {
    private final TranslationRepository translationRepository;
    private final KeyRepository keyRepository;

    @Transactional
    @Override
    public List<Translation> executeWithReturn(String language) {
        var keys = keyRepository.findAll();
        return translationRepository.findAllByLanguageAndKey_IdIn(language, keys.stream().map(KeyEntity::getId).toList())
                .stream()
                .map(e -> new Translation(e.getKey().getKey(), e.getValue()))
                .toList();
    }
}
