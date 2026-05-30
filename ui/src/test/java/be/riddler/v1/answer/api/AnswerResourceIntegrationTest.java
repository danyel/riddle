package be.riddler.v1.answer.api;

import be.riddler.common.resource.AbstractResourceIntegrationTest;
import be.riddler.v1.fixture.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AnswerResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 26/04/2026
 */
@DisplayName("Answer Resource")
class AnswerResourceIntegrationTest extends AbstractResourceIntegrationTest {

    @Test
    void findByQuestionId() {
        webTestClient.get()
                .uri("%s/question/%s".formatted(AnswerResource.BASE, Fixture.Question.dbId))
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