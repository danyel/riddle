package be.riddler.v1.activity.domain.feature;

import be.riddler.v1.activity.domain.ActivityDetail;
import be.riddler.v1.activity.domain.CreateActivity;
import be.riddler.v1.activity.mapper.ActivityMapper;
import be.riddler.v1.activity.repository.ActivityRepository;
import be.riddler.v1.common.domain.feature.DomainFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * CreateActivityFeature
 *
 * @author dnoulet
 * @version 1.0.0 09/05/2026
 */
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateActivityFeature implements DomainFeature<CreateActivity, ActivityDetail> {
    private final ActivityRepository activityRepository;

    @Override
    public ActivityDetail executeWithReturn(CreateActivity createActivity) {
        var activityEntity = ActivityMapper.fromCreateActivity(createActivity);
        activityRepository.save(activityEntity);
        return ActivityMapper.fromActivityEntity(activityEntity);
    }
}
