package be.riddler.v1.activity.api;

import be.riddler.v1.activity.port.CreateActivityOutPort;
import be.riddler.v1.activity.port.GetActivitiesOutPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * ActivityResource
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping(path = "/v1/activities")
public class ActivityResource implements ActivityApi {
    private final CreateActivityOutPort createActivityOutPort;
    private final GetActivitiesOutPort getActivitiesOutPort;

    @Override
    public List<ActivityDetail> getActivities(UUID activityId) {
        return getActivitiesOutPort.getActivities(activityId);
    }

    @Override
    public ActivityDetail create(CreateActivity createActivity) {
        return createActivityOutPort.create(createActivity);
    }
}

