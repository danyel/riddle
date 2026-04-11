package be.riddler.question.bff;

import be.riddler.question.port.out.QuestionRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * QuestionService
 *
 * @author dnoulet
 * @version 1.0.0 11/04/2026
 */
@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class QuestionService {
    private final QuestionRepository questionRepository;

    public @NonNull List<@NonNull Question> getQuestions() {
        return questionRepository.getQuestions()
                .stream()
                .map(question -> new Question(question.getId(), question.getQuestion(), question.getType()))
                .toList();
    }
}
