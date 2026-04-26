package be.riddler.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenApiConfiguration
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@Configuration
@OpenAPIDefinition
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "X-Authorization",
                        type = SecuritySchemeType.APIKEY,
                        in = SecuritySchemeIn.HEADER,
                        paramName = "X-Authorization"
                )
        }
)
class OpenApiConfiguration {
    @Bean
    OpenAPI openAPI() {
        var info = new Info();
        var contact = new Contact();
        contact.setName("Daniel Noulet");
        info.setTitle("Riddler API");
        info.setDescription("Open API definition for the Riddler api");
        info.setVersion("0.0.1");
        info.contact(contact);
        var securityRequirement = new SecurityRequirement();
        securityRequirement.put("namer", List.of("X-Authorization"));
        return new OpenAPI()
                .security(List.of(securityRequirement))
                .info(info);
    }
}
