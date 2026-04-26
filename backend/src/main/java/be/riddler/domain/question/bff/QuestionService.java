package be.riddler.domain.question.bff;

import be.riddler.domain.question.api.Question;
import be.riddler.domain.question.api.QuestionApi;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.PermitAll;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * QuestionService
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@BrowserCallable
@PermitAll
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class QuestionService {
    private final QuestionApi questionApi;

    public @NonNull List<@NonNull Question> getQuestions() {
        return questionApi.getQuestions();
    }


    public @NonNull Question get(UUID uuid) {
        return questionApi.findById(uuid);
    }
}
