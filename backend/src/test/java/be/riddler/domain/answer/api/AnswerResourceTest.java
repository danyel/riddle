package be.riddler.domain.answer.api;

import be.riddler.common.resource.AbstractResourceTest;
import org.junit.jupiter.api.Test;

/**
 * AnswerResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
class AnswerResourceTest extends AbstractResourceTest {

    @Test
    void findByQuestion() {
        webTestClient.get()
                .uri("/v1/answers/a2b20b03-9d96-41d4-8e83-a35b21c21fe7")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].id")
                .isEqualTo("a9e8cbaa-e047-41bb-9554-d4b08352a08a")
                .jsonPath("$[1].id")
                .isEqualTo("18adc354-d7ea-4f97-a579-5628f3cfa0cb")
                .jsonPath("$[2].id")
                .isEqualTo("a599e7e2-2e67-4638-9638-d47e82f909d1");
    }
}