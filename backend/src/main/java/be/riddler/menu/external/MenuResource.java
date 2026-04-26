package be.riddler.menu.external;

import be.riddler.menu.bff.Menu;
import be.riddler.menu.port.MenuRepository;
import be.riddler.question.bff.Question;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MenuResource
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/menu/v1")
@OpenAPIDefinition(
        info = @Info(
                title = "Menu API",
                description = "Open API definition for the Menu api",
                version = "0.0.1",
                contact = @Contact(name = "Daniel Noulet")
        ),
        tags = @Tag(name = "menu")
)
public class MenuResource {
    private final MenuRepository menuRepository;

    @Operation(
            method = "GET",
            tags = "menu",
            summary = "Retrieves the menu of the logged in user",
            parameters = {
                    @Parameter(name = "Authorization", schema = @Schema(implementation = String.class))
            },
            operationId = "getQuestions",
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
    public List<Menu> menu() {
        return menuRepository.findAll("")
                .stream()
                .map(menu -> new Menu(menu.getPath(), menu.getLabel(), menu.getIcon(), menu.getOrder()))
                .toList();
    }
}
