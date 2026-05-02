package be.riddler.v1.ui.api;

import be.riddler.common.resource.AbstractResourceTest;
import be.riddler.configuration.filter.AuthorizationFilter;
import org.junit.jupiter.api.Test;

/**
 * ComponentsResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
class UiResourceTest extends AbstractResourceTest {

    @Test
    void icons() {
        webTestClient.get()
                .uri("/v1/ui/icons")
                .header(AuthorizationFilter.X_AUTHORIZATION, xAuthenticationContent())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0]")
                .isEqualTo("vaadin:abacus");
    }

    @Test
    void name() {
        System.out.println(xAuthenticationContent());
    }
}