package be.riddler.v1.answer.mapper;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.entity.AnswerEntity;
import be.riddler.v1.answer.entity.SolutionEntity;
import lombok.experimental.UtilityClass;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * AnswerMapper
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@UtilityClass
public final class AnswerMapper {
    public static @NonNull AnswerEntity fromCreateAnswer(@NonNull CreateAnswer answer) {
        return AnswerEntity.builder().questionId(answer.questionId()).build();
    }

    public static @NonNull Answer fromAnswerEntity(@NonNull AnswerEntity answerEntity) {
        var solutions = Objects.requireNonNullElse(answerEntity.getSolutions(), List.<SolutionEntity>of())
                .stream()
                .map(SolutionMapper::fromEntityToSolution)
                .toList();
        return new Answer(answerEntity.getId(), solutions, answerEntity.getQuestionId());
    }
}
