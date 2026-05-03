package be.riddler.v1.question.adapter;

import be.riddler.v1.question.adapter.entity.QuestionEntity;
import be.riddler.v1.question.adapter.repository.QuestionRepository;
import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.port.QuestionOutPort;
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
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class QuestionOutAdapter implements QuestionOutPort {
    private final QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(questionMapper())
                .toList();
    }

    @Override
    public Question findById(UUID uuid) {
        return questionRepository.findById(uuid)
                .map(questionMapper())
                .orElseThrow();
    }

    private static @NonNull Function<QuestionEntity, Question> questionMapper() {
        return e -> new Question(e.getId(), e.getQuestion(), e.getType());
    }

    @Override
    public Question create(Question question) {
        var questionEntity = QuestionEntity.builder().question(question.getQuestion()).type(question.getType()).build();
        return questionMapper()
                .apply(questionRepository.save(questionEntity));
    }

    @Override
    public Question update(Question question) {
        var questionEntity = questionRepository.findById(question.getId());
        questionEntity.ifPresent(e -> {
            e.setQuestion(question.getQuestion());
            e.setType(question.getType());
            questionRepository.save(e);
        });
        return question;
    }
}
