package be.riddler.v1.answer.api;

import be.riddler.v1.answer.domain.Answer;
import be.riddler.v1.answer.domain.CreateAnswer;
import be.riddler.v1.answer.domain.UpdateAnswer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

/**
 * AnswerApi
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Tag(name = "answers")
public interface AnswerApi {
    @SuppressWarnings("SpringMvcPathVariableDeclarationInspection")
    @Operation(
            method = "GET",
            tags = "answers",
            summary = "Retrieves the question by id",
            operationId = "findByQuestion",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Answer.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetMapping(path = "/question/{questionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Answer> findByQuestionId(@PathVariable(name = "questionId") UUID questionId);

    @Operation(
            method = "POST",
            tags = "answers",
            summary = "Creates an answer",
            operationId = "create",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(schema = @Schema(implementation = CreateAnswer.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Answer.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Answer create(@RequestBody CreateAnswer createAnswer);

    @Operation(
            method = "PUT",
            tags = "answers",
            summary = "Updates an answer by answer id",
            operationId = "update",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(schema = @Schema(implementation = UpdateAnswer.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Answer.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Answer update(@PathVariable(name = "id") UUID answerId, @RequestBody UpdateAnswer updateAnswer);
}
