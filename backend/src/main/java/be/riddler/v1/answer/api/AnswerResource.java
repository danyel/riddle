package be.riddler.v1.answer.api;

import be.riddler.api.v1.answer.Answer;
import be.riddler.api.v1.answer.AnswerApi;
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
    public List<Answer> findByQuestion(UUID id) {
        return answerOutPort.findByQuestion(id)
                .stream()
                .map(AnswerResource::toBff)
                .toList();
    }

    private static Answer toBff(be.riddler.v1.answer.domain.Answer answer) {
        return new Answer(answer.getId(), answer.getValue(), answer.getQuestionId());
    }
}
