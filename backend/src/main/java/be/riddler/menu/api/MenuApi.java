package be.riddler.menu.api;

import be.riddler.menu.bff.Menu;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * MenuApi
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Menu API",
                description = "Open API definition for the Menu api",
                version = "0.0.1",
                contact = @Contact(name = "Daniel Noulet")
        ),
        tags = @Tag(name = "menu")
)
public interface MenuApi {
    @Operation(
            method = "GET",
            tags = "menu",
            summary = "Retrieves the menu of the logged in user",
            operationId = "menu",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Menu.class)))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @GetMapping(path = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Menu> menu();
}
