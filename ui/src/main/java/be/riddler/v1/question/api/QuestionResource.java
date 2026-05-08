package be.riddler.v1.question.api;

import be.riddler.v1.question.port.QuestionOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * QuestionResource
 *
 * @author dnoulet
 * @version 1.0.0 25/04/2026
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@RequestMapping(path = "/v1/questions")
class QuestionResource implements QuestionApi {
    private final QuestionOutPort questionOutPort;

    @Override
    public List<Question> getQuestions() {
        return questionOutPort.getQuestions()
                .stream()
                .map(QuestionResource::map)
                .toList();
    }

    private static @NonNull Question map(be.riddler.v1.question.domain.Question question) {
        return new Question(question.getId(), question.getQuestion(), question.getType());
    }

    @Override
    public Question findById(UUID uuid) {
        return map(questionOutPort.findById(uuid));
    }

    @Override
    public Question create(CreateQuestion createQuestion) {
        var question = new be.riddler.v1.question.domain.Question(createQuestion.question(), createQuestion.type());
        return map(questionOutPort.create(question));
    }

    @Override
    public void delete(UUID id) {
        questionOutPort.delete(id);
    }

    @Override
    public Question update(UUID id, UpdateQuestion updateQuestion) {
        return map(questionOutPort.update(new be.riddler.v1.question.domain.Question(id, updateQuestion.question(), updateQuestion.type())));
    }
}
