package be.riddler.v1.activity.domain.feature;

import be.riddler.v1.activity.domain.ActivityDetail;
import be.riddler.v1.activity.domain.GetActivities;
import be.riddler.v1.activity.mapper.ActivityMapper;
import be.riddler.v1.activity.repository.ActivityRepository;
import be.riddler.v1.common.domain.feature.DomainFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
public class GetActivitiesFeature implements DomainFeature<GetActivities, List<ActivityDetail>> {
    private final ActivityRepository activityRepository;

    @Override
    public List<ActivityDetail> executeWithReturn(GetActivities getActivities) {
        return activityRepository.findAllByUsernameAndQuestionId(getActivities.username(), getActivities.questionId())
                .stream()
                .map(ActivityMapper::fromActivityEntity)
                .toList();
    }
}
