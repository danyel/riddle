package be.riddler.v1.activity.bff;

import be.riddler.v1.activity.api.ActivityApi;
import be.riddler.v1.activity.api.CreateActivity;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * ActivityEndpoint
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Slf4j
@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityEndpoint {
    private final ActivityApi activityApi;

    // Batch logging reduces network request spam drastically
    public void logActions(List<CreateActivity> activities) {
        for (CreateActivity activity : activities) {
            log.info("User Activity Tracked: User executed [{}] on [{}] for question [{}] at {}",
                    activity.actionType(), activity.elementId(), activity.questionId(), activity.timestamp());
            activityApi.create(activity);
        }
    }
}