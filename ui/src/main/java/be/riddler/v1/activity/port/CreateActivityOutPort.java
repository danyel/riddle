package be.riddler.v1.activity.port;

import be.riddler.v1.activity.api.ActivityDetail;
import be.riddler.v1.activity.api.CreateActivity;

/**
 * CreateActivityOutPort
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
public interface CreateActivityOutPort {
    ActivityDetail create(CreateActivity createActivity);
}
