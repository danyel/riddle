package be.riddler.v1.answer.adapter;

import be.riddler.v1.answer.adapter.entity.AnswerEntity;
import be.riddler.v1.answer.adapter.repository.AnswerRepository;
import be.riddler.v1.answer.domain.Answer;
import be.riddler.v1.answer.port.AnswerOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * QuestionLocalRepository
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Component
class AnswerOutAdapter implements AnswerOutPort {
    private final AnswerRepository answerRepository;
    @Override
    public List<Answer> findByQuestion(UUID questionId) {
        return answerRepository.findAllByQuestionId(questionId)
                .stream()
                .map(answerMapper())
                .toList();
    }

    @Override
    public Answer create(Answer answer) {
        return answerMapper().apply(answerRepository.save(AnswerEntity.builder().value(answer.value()).questionId(answer.questionId()).build()));
    }

    private static @NonNull Function<AnswerEntity, Answer> answerMapper() {
        return e -> new Answer(e.getId(), e.getValue(), e.getQuestionId());
    }
}
