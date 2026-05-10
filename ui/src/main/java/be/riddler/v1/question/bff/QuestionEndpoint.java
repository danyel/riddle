package be.riddler.v1.question.bff;

import be.riddler.v1.question.api.QuestionClient;
import be.riddler.v1.question.domain.CreateQuestion;
import be.riddler.v1.question.domain.Question;
import be.riddler.v1.question.domain.UpdateQuestion;
import com.vaadin.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
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
@RolesAllowed("ADMIN")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class QuestionEndpoint {
    private final QuestionClient questionApi;

    public @NonNull List<@NonNull Question> getQuestions() {
        return questionApi.getQuestions();
    }


    public @NonNull Question get(UUID uuid) {
        return questionApi.findById(uuid);
    }

    public @NonNull Question update(UUID id, UpdateQuestion updateQuestion) {
        return questionApi.update(id, updateQuestion);
    }

    public void delete(@NonNull UUID uuid) {
        questionApi.delete(uuid);
    }

    public Question create(CreateQuestion createQuestion) {
        return questionApi.create(createQuestion);
    }
}
