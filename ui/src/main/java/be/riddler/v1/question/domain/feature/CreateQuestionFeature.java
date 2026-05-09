package be.riddler.v1.question.domain.feature;

import be.riddler.v1.question.domain.CreateQuestion;
import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.mapper.QuestionMapper;
import be.riddler.v1.question.repository.QuestionRepository;
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
public class CreateQuestionFeature {
    private final QuestionRepository questionRepository;

    public @NonNull Question executeWithReturn(@NonNull CreateQuestion createQuestion) {
        var question = QuestionMapper.fromCreateQuestion(createQuestion);
        var questionEntity = QuestionMapper.fromQuestion(question);
        questionRepository.save(questionEntity);
        return QuestionMapper.fromQuestionEntity(questionEntity);
    }
}
