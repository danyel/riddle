package be.riddler.v1.activity.port;

import be.riddler.v1.activity.api.ActivityDetail;

import java.util.List;
import java.util.UUID;

/**
 * CreateActivityOutPort
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public interface GetActivitiesOutPort {
    List<ActivityDetail> getActivities(UUID activityId);
}
