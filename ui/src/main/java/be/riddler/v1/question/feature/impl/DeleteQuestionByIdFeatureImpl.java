package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.client.model.QuestionId;
import be.riddler.v1.question.feature.DeleteQuestionByIdFeature;
import be.riddler.v1.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteQuestionByIdFeatureImpl implements DeleteQuestionByIdFeature {
    private final QuestionRepository questionRepository;

    @Override
    public void execute(@NonNull QuestionId questionId) {
        if (!questionRepository.existsById(questionId.id())) {
            throw new EntityNotFoundException(questionId.id().toString());
        }
        questionRepository.deleteById(questionId.id());
    }
}
