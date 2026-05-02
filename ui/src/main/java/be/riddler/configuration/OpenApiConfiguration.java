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
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
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
    OpenAPI openAPI(
            @Value("${swagger.root}") String swaggerUrl,
            @Value("${spring.application.name}") String applicationName,
            @Value("${spring.application.version}") String version
    ) {
        var info = new Info();
        var contact = new Contact();
        var server = new Server();
        server.setUrl(swaggerUrl);
        contact.setName("Daniel Noulet");
        info.setTitle(applicationName);
        info.setDescription("Open API definition for the Riddler api");
        info.setVersion(version);
        info.contact(contact);
        var securityRequirement = new SecurityRequirement();
        securityRequirement.put("namer", List.of("X-Authorization"));
        return new OpenAPI()
                .servers(List.of(server))
                .security(List.of(securityRequirement))
                .info(info);
    }
}
