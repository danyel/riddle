package be.riddler.domain.ui.api;

import be.riddler.common.resource.AbstractResourceTest;
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
                .uri("/v1/components")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0]")
                .isEqualTo("vaadin:abacus");
    }
}