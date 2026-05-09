package be.riddler.v1.question.domain.feature;

import be.riddler.v1.common.domain.feature.DomainFeature;
import be.riddler.v1.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DeleteQuestionByIdFeature implements DomainFeature<UUID, Void> {
    private final QuestionRepository questionRepository;

    @Override
    public void execute(UUID id) {
        if (!questionRepository.existsById(id)) {
            throw new EntityNotFoundException(id.toString());
        }
        questionRepository.deleteById(id);
    }
}
