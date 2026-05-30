package be.riddler.v1.activity.api;

import be.riddler.common.resource.AbstractResourceIntegrationTest;
import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.CreateActivity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static be.riddler.configuration.filter.JwtAuthenticationFilter.JWT_HEADER_AND_PAYLOAD;
import static be.riddler.configuration.filter.JwtAuthenticationFilter.JWT_SIGNATURE;
import static be.riddler.v1.activity.api.ActivityResource.BASE;
import static be.riddler.v1.fixture.Fixture.Question.dbId;
import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * ActivityResourceTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Activity Resource")
class ActivityResourceIntegrationTest extends AbstractResourceIntegrationTest {


    @DisplayName("Given we have added an activity when fetching the activity then activityDetail will be return")
    @Test
    void getActivities() {
        var instant = LocalDateTime.now().toInstant(UTC);
        var actionType = "action";
        var elementId = "element";
        var additionalData = "additional data";
        var createActivity = new CreateActivity(dbId, actionType, elementId, instant, additionalData);

        var activityDetailCreated = webTestClient.post()
                .uri(BASE)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .cookie(JWT_SIGNATURE, jwtSignature())
                .cookie(JWT_HEADER_AND_PAYLOAD, jwtHeaderAndPayload())
                .bodyValue(createActivity)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(ActivityDetail.class)
                .returnResult()
                .getResponseBody();

        assert activityDetailCreated != null;

        var activityDetails = webTestClient.get()
                .uri("%s/%s".formatted(BASE, dbId))
                .accept(APPLICATION_JSON)
                .cookie(JWT_SIGNATURE, jwtSignature())
                .cookie(JWT_HEADER_AND_PAYLOAD, jwtHeaderAndPayload())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ActivityDetail[].class)
                .returnResult()
                .getResponseBody();

        assertNotNull(activityDetails);
        var activityDetail = activityDetails[0];

        assertEquals(actionType, activityDetail.actionType());
        assertEquals(elementId, activityDetail.elementId());
        assertEquals(additionalData, activityDetail.additionalData());
    }
}