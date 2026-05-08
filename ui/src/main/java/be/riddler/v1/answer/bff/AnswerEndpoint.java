package be.riddler.v1.answer.bff;

import be.riddler.v1.answer.api.Answer;
import be.riddler.v1.answer.api.AnswerApi;
import be.riddler.v1.answer.api.CreateAnswer;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.PermitAll;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * AnswerEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@PermitAll
@BrowserCallable
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AnswerEndpoint {
    private final AnswerApi answerApi;

    public @NonNull List<@NonNull Answer> findByQuestion(UUID questionId) {
        return answerApi.findByQuestionId(questionId);
    }

    public @NonNull Answer create(@NonNull CreateAnswer createAnswer) {
        return answerApi.create(createAnswer);
    }
}
