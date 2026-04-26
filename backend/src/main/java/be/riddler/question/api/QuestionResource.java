package be.riddler.question.api;

import be.riddler.question.bff.Question;
import be.riddler.question.port.QuestionOutPort;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@RequestMapping(path = "/question/v1")
class QuestionResource implements QuestionApi {
    private final QuestionOutPort questionOutPort;


    @Override
    public List<Question> getQuestions() {
        return questionOutPort.getQuestions()
                .stream()
                .map(QuestionResource::map)
                .toList();
    }

    private static @NonNull Question map(be.riddler.question.domain.Question question) {
        return new Question(question.getId(), question.getQuestion(), question.getType());
    }

    @Override
    public Question findById(UUID uuid) {
        return map(questionOutPort.findById(uuid));
    }
}
