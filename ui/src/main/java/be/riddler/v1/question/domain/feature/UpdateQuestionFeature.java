package be.riddler.v1.question.domain.feature;

import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.domain.UpdateWithId;
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
public class UpdateQuestionFeature {
    private final QuestionRepository questionRepository;

    public @NonNull Question executeWithReturn(@NonNull UpdateWithId updateWithId) {
        var questionEntity = questionRepository.findById(updateWithId.questionId().id());
        if (questionEntity.isPresent()) {
            questionEntity.ifPresent(e -> {
                e.setQuestion(updateWithId.question().question());
                e.setType(updateWithId.question().type());
                questionRepository.save(e);
            });
            return QuestionMapper.fromQuestionEntity(questionEntity.get());
        } else {
            throw new EntityNotFoundException("Question with id " + updateWithId.questionId().id() + " not found");
        }
    }
}
