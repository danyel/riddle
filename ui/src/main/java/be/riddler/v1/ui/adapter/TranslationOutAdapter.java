package be.riddler.v1.ui.adapter;

import be.riddler.v1.ui.adapter.entity.KeyEntity;
import be.riddler.v1.ui.adapter.repository.KeyRepository;
import be.riddler.v1.ui.adapter.repository.TranslationRepository;
import be.riddler.v1.ui.domain.Translation;
import be.riddler.v1.ui.port.TranslationOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TranslationOutAdapter
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TranslationOutAdapter implements TranslationOutPort {
    private final TranslationRepository translationRepository;
    private final KeyRepository keyRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Translation> getTranslations(String language) {
        var keys = keyRepository.findAll();
        return translationRepository.findAllByLanguageAndKey_IdIn(language, keys.stream().map(KeyEntity::getId).toList())
                .stream()
                .map(e -> new Translation(e.getKey().getKey(), e.getValue()))
                .toList();
    }
}
