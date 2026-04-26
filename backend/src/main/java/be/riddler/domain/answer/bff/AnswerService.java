package be.riddler.domain.answer.bff;

import be.riddler.domain.answer.api.Answer;
import be.riddler.domain.answer.api.AnswerApi;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.PermitAll;
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
@PermitAll
@BrowserCallable
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AnswerService {
    private final AnswerApi answerApi;

    public @NonNull List<@NonNull Answer> findByQuestion(UUID id) {
        return answerApi.findByQuestion(id);
    }
}
