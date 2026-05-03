package be.riddler.v1.ui.adapter;

import be.riddler.v1.ui.adapter.repository.KeyRepository;
import be.riddler.v1.ui.adapter.repository.TranslationRepository;
import be.riddler.v1.ui.domain.Translation;
import be.riddler.v1.ui.port.TranslateOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TranslationOutAdapter
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TranslateOutAdapter implements TranslateOutPort {
    private final TranslationRepository translationRepository;
    private final KeyRepository keyRepository;

    @Transactional(readOnly = true)
    @Override
    public Translation translate(String language, String key) {
        var keyEntity = keyRepository.findByKey(key);
        return translationRepository.findByLanguageAndKey_Id(language, keyEntity.getId())
                .map(e -> new Translation(e.getKey().getKey(), e.getValue()))
                .orElse(new Translation(key, "UNKNOWN"));
    }
}
