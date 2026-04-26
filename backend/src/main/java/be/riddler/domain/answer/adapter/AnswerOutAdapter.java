package be.riddler.domain.answer.adapter;

import be.riddler.domain.answer.adapter.repository.AnswerRepository;
import be.riddler.domain.answer.domain.Answer;
import be.riddler.domain.answer.port.AnswerOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
                .map(e -> new Answer(e.getId(), e.getValue(), e.getQuestionId()))
                .toList();
    }
}
