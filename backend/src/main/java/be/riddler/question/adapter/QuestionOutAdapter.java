package be.riddler.question.adapter;

import be.riddler.question.adapter.repository.QuestionRepository;
import be.riddler.question.domain.Question;
import be.riddler.question.port.QuestionOutPort;
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
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class QuestionOutAdapter implements QuestionOutPort {
    private final QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(e -> new Question(e.getId(), e.getQuestion(), e.getType()))
                .toList();
    }

    @Override
    public Question findById(UUID uuid) {
        return questionRepository.findById(uuid)
                .map(e -> new Question(e.getId(), e.getQuestion(), e.getType()))
                .orElseThrow();
    }
}
