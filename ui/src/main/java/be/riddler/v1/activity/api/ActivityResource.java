package be.riddler.v1.activity.api;

import be.riddler.v1.activity.client.ActivityClient;
import be.riddler.v1.activity.client.model.ActivityDetail;
import be.riddler.v1.activity.client.model.CreateActivity;
import be.riddler.v1.activity.client.model.GetActivities;
import be.riddler.v1.activity.feature.CreateActivityFeature;
import be.riddler.v1.activity.feature.GetActivitiesFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;

/**
 * ActivityResource
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@RestController
@RequiredArgsConstructor(access = PACKAGE)
@RequestMapping(path = ActivityResource.BASE)
public class ActivityResource implements ActivityClient {
    public static final String BASE = "/v1/activities";
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

