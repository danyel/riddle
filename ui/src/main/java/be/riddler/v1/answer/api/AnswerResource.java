package be.riddler.v1.answer.api;

import be.riddler.v1.answer.port.AnswerOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * AnswerResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@RequestMapping(path = "/v1/answers")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AnswerResource implements AnswerApi {
    private final AnswerOutPort answerOutPort;

    @Override
    public List<Answer> findByQuestionId(UUID questionId) {
        return answerOutPort.findByQuestion(questionId)
                .stream()
                .map(AnswerResource::toBff)
                .toList();
    }

    @Override
    public Answer create(CreateAnswer createAnswer) {
        return toBff(answerOutPort.create(new be.riddler.v1.answer.domain.Answer(createAnswer.value(), createAnswer.questionId())));
    }

    private static Answer toBff(be.riddler.v1.answer.domain.Answer answer) {
        return new Answer(answer.id(), answer.value(), answer.questionId());
    }
}
