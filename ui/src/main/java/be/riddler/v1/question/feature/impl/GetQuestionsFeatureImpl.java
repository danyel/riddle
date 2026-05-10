package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.feature.GetQuestionsFeature;
import be.riddler.v1.question.mapper.QuestionMapper;
import be.riddler.v1.question.repository.QuestionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GetQuestionsFeatureImpl implements GetQuestionsFeature {
    private final QuestionRepository questionRepository;

    @Override
    public @NonNull List<@NonNull Question> findAll() {
        return questionRepository.findAll()
                .stream()
                .map(QuestionMapper::fromQuestionEntity)
                .toList();
    }
}
