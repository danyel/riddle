package be.riddler.v1.activity.feature;

import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.GetActivities;
import be.riddler.v1.activity.mapper.ActivityMapper;
import be.riddler.v1.activity.repository.ActivityRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GetActivitiesFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetActivitiesFeature {
    private final ActivityRepository activityRepository;

    public @NonNull List<@NonNull ActivityDetail> getActivities(@NonNull GetActivities getActivities) {
        return activityRepository.findAllByUsernameAndQuestionId(getActivities.username(), getActivities.questionId())
                .stream()
                .map(ActivityMapper::fromActivityEntity)
                .toList();
    }
}
