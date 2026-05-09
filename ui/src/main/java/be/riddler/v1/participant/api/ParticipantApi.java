package be.riddler.v1.participant.api;

import be.riddler.v1.participant.domain.ParticipantDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

/**
 * ParticipantApi
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public interface ParticipantApi {
    @Operation(
            method = "GET",
            tags = "participants",
            summary = "Retrieve the participant corresponding to the id",
            operationId = "findById",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ParticipantDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @NonNull ParticipantDetail findById(@PathVariable(name = "id") @NonNull UUID participantId);

    @Operation(
            method = "GET",
            tags = "participants",
            summary = "Retrieve all participants",
            operationId = "findAll",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ParticipantDetail.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @NonNull List<@NonNull ParticipantDetail> findAll();
}
