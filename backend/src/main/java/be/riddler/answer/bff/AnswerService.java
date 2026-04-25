package be.riddler.answer.bff;

import be.riddler.answer.port.AnswerRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * AnswerService
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@AnonymousAllowed
@BrowserCallable
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AnswerService {
    private final AnswerRepository answerRepository;

    public @NonNull List<@NonNull Answer> findByQuestion(UUID id) {
        return answerRepository.findByQuestion(id)
                .stream()
                .map(AnswerService::toBff)
                .toList();
    }

    private static Answer toBff(be.riddler.answer.domain.Answer answer) {
        return new Answer(answer.getId(), answer.getValue(), answer.getQuestionId());
    }
}
