package be.riddler.v1.question.feature.impl;

import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.feature.GetQuestionsByIdsFeature;
import be.riddler.v1.question.mapper.QuestionMapper;
import be.riddler.v1.question.repository.QuestionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * CreateQuestionFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GetQuestionsByIdsFeatureImpl implements GetQuestionsByIdsFeature {
    private final QuestionRepository questionRepository;

    @Override
    public @NonNull List<@NonNull Question> findAll(List<UUID> questionIds) {
        return questionRepository.findAllById(questionIds)
                .stream()
                .map(QuestionMapper::fromQuestionEntity)
                .toList();
    }
}
