package be.riddler.v1.activity.api;

import be.riddler.v1.activity.domain.ActivityDetail;
import be.riddler.v1.activity.domain.CreateActivity;
import be.riddler.v1.activity.domain.GetActivities;
import be.riddler.v1.activity.feature.CreateActivityFeature;
import be.riddler.v1.activity.feature.GetActivitiesFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * ActivityResource
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@RequestMapping(path = "/v1/activities")
public class ActivityResource implements ActivityClient {
    private final CreateActivityFeature createActivityFeature;
    private final GetActivitiesFeature getActivitiesFeature;

    @Override
    public List<ActivityDetail> getActivities(UUID activityId) {
        return getActivitiesFeature.getActivities(new GetActivities(activityId, Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName()));
    }

    @Override
    public ActivityDetail create(CreateActivity createActivity) {
        return createActivityFeature.create(createActivity);
    }
}

