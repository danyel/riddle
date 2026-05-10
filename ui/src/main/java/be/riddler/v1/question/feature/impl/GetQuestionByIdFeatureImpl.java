package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.client.model.Question;
import be.riddler.v1.question.client.model.QuestionId;
import be.riddler.v1.question.feature.GetQuestionByIdFeature;
import be.riddler.v1.question.mapper.QuestionMapper;
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
class GetQuestionByIdFeatureImpl implements GetQuestionByIdFeature {
    private final QuestionRepository questionRepository;

    @Override
    public @NonNull Question byQuestionId(@NonNull QuestionId questionId) {
        return questionRepository.findById(questionId.id()).map(QuestionMapper::fromQuestionEntity).orElseThrow(EntityNotFoundException::new);
    }
}
