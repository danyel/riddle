package be.riddler.v1.answer.mapper;

import be.riddler.v1.answer.client.model.Answer;
import be.riddler.v1.answer.client.model.CreateAnswer;
import be.riddler.v1.answer.entity.AnswerEntity;
import lombok.experimental.UtilityClass;

/**
 * AnswerMapper
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@UtilityClass
public class AnswerMapper {
    public static AnswerEntity fromCreateAnswer(CreateAnswer answer) {
        return AnswerEntity.builder().value(answer.value()).questionId(answer.questionId()).build();
    }

    public static Answer fromAnswerEntity(AnswerEntity answerEntity) {
        return new Answer(answerEntity.getId(), answerEntity.getValue(), answerEntity.getQuestionId());
    }
}
