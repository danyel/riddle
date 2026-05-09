package be.riddler.v1.activity.feature;

import be.riddler.v1.activity.domain.ActivityDetail;
import be.riddler.v1.activity.domain.CreateActivity;
import be.riddler.v1.activity.mapper.ActivityMapper;
import be.riddler.v1.activity.repository.ActivityRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * CreateActivityFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateActivityFeature {
    private final ActivityRepository activityRepository;

    public @NonNull ActivityDetail create(@NonNull CreateActivity createActivity) {
        var activityEntity = ActivityMapper.fromCreateActivity(createActivity);
        activityRepository.save(activityEntity);
        return ActivityMapper.fromActivityEntity(activityEntity);
    }
}
