package be.riddler.v1.activity.mapper;

import be.riddler.v1.activity.client.model.CreateActivity;
import be.riddler.v1.activity.entity.ActivityEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static be.riddler.v1.fixture.Fixture.Category.categoryId;
import static be.riddler.v1.fixture.Fixture.Question.questionId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * ActivityMapperTest
 *
 * @author dnoulet
 * @version 1.0.0 30/05/2026
 */
@DisplayName("Activity Mapper")
class ActivityMapperTest {

    @Test
    void fromCreateActivity() {
        var activityEntity = ActivityMapper.fromCreateActivity(new CreateActivity(questionId, "action", "element", LocalDateTime.now().toInstant(ZoneOffset.UTC), "additional data"));

        assertNotNull(activityEntity);
        assertEquals(questionId, activityEntity.getQuestionId());
        assertEquals("element", activityEntity.getElementId());
        assertEquals("action", activityEntity.getActionType());
        assertEquals("additional data", activityEntity.getAdditionalData());
    }

    @Test
    void fromActivityEntity() {
        var activityDetail = ActivityMapper.fromActivityEntity(ActivityEntity.builder()
                .id(categoryId)
                .elementId("elementId")
                .questionId(questionId)
                .additionalData("additionalData")
                .actionType("action")
                .build());
        assertNotNull(activityDetail);
        assertEquals("elementId", activityDetail.elementId());
        assertEquals("action", activityDetail.actionType());
        assertEquals("additionalData", activityDetail.additionalData());
        assertEquals(questionId, activityDetail.questionId());
        assertEquals(categoryId, activityDetail.id());

    }
}